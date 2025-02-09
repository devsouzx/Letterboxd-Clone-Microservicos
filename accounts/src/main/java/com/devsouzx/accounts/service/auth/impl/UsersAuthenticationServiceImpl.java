package com.devsouzx.accounts.service.auth.impl;

import com.devsouzx.accounts.database.model.User;
import com.devsouzx.accounts.database.repository.UserRepository;
import com.devsouzx.accounts.dto.user.*;
import com.devsouzx.accounts.handler.exceptions.*;
import com.devsouzx.accounts.service.jwt.JwtService;
import com.devsouzx.accounts.service.redis.RedisService;
import com.devsouzx.accounts.service.auth.IUsersAuthenticationService;
import com.devsouzx.accounts.util.PasswordValidatorHelper;
import com.devsouzx.accounts.util.RandomNumberUtil;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UsersAuthenticationServiceImpl implements IUsersAuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String REGISTRATION_TOPIC = "letterboxdclone-new-register";
    private static final String PASSWORD_RESET_TOPIC = "letterboxdclone-password-reset";

    public UsersAuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RedisService redisService, JwtService jwtService, AuthenticationManager authenticationManager, KafkaTemplate<String, String> kafkaTemplate) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.redisService = redisService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    @Transactional
    public TokenResponse register(UserRegistrationRequest request) throws Exception {
        Optional<User> user = userRepository.findByUsername(request.getUsername());
        if (user.isPresent()) throw new UsernameAlreadyExistsException("Someone has already taken that username.");
        user = userRepository.findByEmail(request.getEmail());
        if (user.isPresent()) throw new EmailAlreadyExistsException("That email address is already associated with an account.");

        if (!PasswordValidatorHelper.isValidPassword(request.getPassword())) throw new InvalidPasswordException();

        userRepository.save(User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .username(request.getUsername())
                .adultContent(false)
                .confirmedEmail(false)
                .includeProfileInMembers(true)
                .customPosters(true)
                .registrationTime(LocalDateTime.now())
                .firstAccess(true)
                .verified(false)
                .role(User.Role.USER)
                .build());

        sendValidationEmail(request.getEmail());

        return authenticate(new AuthRequest(request.getUsername(), request.getPassword(), false), false);
    }

    @Override
    @Transactional
    public TokenResponse authenticate(AuthRequest request, Boolean login) throws Exception {
        Authentication authentication = authenticationManager.authenticate(request.build());
        User user = jwtService.getUser(authentication);

        if (login && !user.getConfirmedEmail()) throw new NotConfirmedEmailException();

        if (login) {
            user.setFirstAccess(false);
            userRepository.save(user);
        }

        TokenResponse tokenResponse = (TokenResponse) redisService.getValue("AUTH_" + user.getIdentifier(), TokenResponse.class);
        if (tokenResponse == null) {
            tokenResponse = jwtService.generateToken(authentication, request.getRememberMe());
            redisService.setValue("AUTH_" + user.getIdentifier(), tokenResponse, TimeUnit.MILLISECONDS, tokenResponse.getExpiresIn(), true);
        }
        return tokenResponse;
    }

    @Override
    @Transactional
    public void sendValidationEmail(String email) throws Exception {
        User user = getUserByEmail(email);

        UserConfirmationCodeResponse confirmationCodeResponse = (UserConfirmationCodeResponse) redisService.getValue(email, UserConfirmationCodeResponse.class);
        if(confirmationCodeResponse == null){
            confirmationCodeResponse = UserConfirmationCodeResponse.builder()
                    .email(email)
                    .confirmationCode(RandomNumberUtil.generateRandomCode(120))
                    .build();

            redisService.setValue(email, confirmationCodeResponse, TimeUnit.MILLISECONDS, 1800000L, true);
        }

        trySendKafkaMessage(confirmationCodeResponse.toString(), REGISTRATION_TOPIC);
        log.error(confirmationCodeResponse.toString());
    }

    @Transactional
    @Override
    public void sendPasswordResetEmail(String email) throws Exception {
        UserResetPasswordResponse userResetPasswordResponse =
                (UserResetPasswordResponse) redisService.getValue("PASSWORDREQUEST_" + email, UserResetPasswordResponse.class);
        if (userResetPasswordResponse == null) {
            userResetPasswordResponse = UserResetPasswordResponse.builder()
                    .email(email)
                    .resetPasswordCode(RandomNumberUtil.generateRandomCode(64))
                    .build();

            redisService.setValue("PASSWORDREQUEST_" + email, userResetPasswordResponse, TimeUnit.MILLISECONDS, 1800000L, true);
        }

        trySendKafkaMessage(userResetPasswordResponse.toString(), PASSWORD_RESET_TOPIC);
        User user = getUserByEmail(email);
        String resetPasswordUrl = "http://localhost:8085/api/v1/user/resetpassword/?id=" + user.getIdentifier() + "&hash=" + userResetPasswordResponse.getResetPasswordCode();
        log.error(resetPasswordUrl);
    }

    @Transactional
    @Override
    public void resetPassword(UserResetPasswordRequest request, UUID id, String code) throws Exception {
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found"));

        UserResetPasswordResponse userResetPasswordResponse = (UserResetPasswordResponse) redisService.getValue("PASSWORDREQUEST_" + user.getEmail(), UserResetPasswordResponse.class);
        if (userResetPasswordResponse == null) throw new Exception("UserResetPasswordResponse does not exists");

        if (!request.getNewPassword().equals(request.getConfirmPassword())) throw new IllegalArgumentException("The passwords you entered were not identical. Please try again.");

        if (!PasswordValidatorHelper.isValidPassword(request.getConfirmPassword())) throw new InvalidPasswordException();

        user.setPassword(passwordEncoder.encode(request.getConfirmPassword()));
        userRepository.save(user);

        redisService.removeKey("PASSWORDREQUEST_" + userResetPasswordResponse.getEmail());
    }

    @Override
    public void validateAccount(String email, String code) throws Exception {
        User user = getUserByEmail(email);

        UserConfirmationCodeResponse confirmationCodeResponse = (UserConfirmationCodeResponse) redisService.getValue(email, UserConfirmationCodeResponse.class);
        if (!confirmationCodeResponse.getConfirmationCode().equals(code)) {
            throw new ConfirmationCodeNotMatchesException();
        }

        user.setConfirmedEmail(true);
        user.setConfirmedEmail(true);
        user.setVerified(true);
        userRepository.save(user);

        redisService.removeKey(email);
    }

    @Transactional
    private void trySendKafkaMessage(String email, String TOPIC) throws Exception {
        try {
            kafkaTemplate.send(TOPIC, email);
            log.error("Mensagem enviada com SUCESSO para o tópico: {}", TOPIC);
        } catch (Exception e) {
            log.error("Erro ao enviar mensagem para o tópico: {}", TOPIC);
        }
    }

    public User getUserByEmail(String email) throws Exception {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }
}
