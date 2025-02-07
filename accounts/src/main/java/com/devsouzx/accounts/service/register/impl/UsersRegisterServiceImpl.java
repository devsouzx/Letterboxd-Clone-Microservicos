package com.devsouzx.accounts.service.register.impl;

import com.devsouzx.accounts.database.model.User;
import com.devsouzx.accounts.database.repository.UserRepository;
import com.devsouzx.accounts.dto.user.UserConfirmationCodeResponse;
import com.devsouzx.accounts.dto.user.UserRegistrationRequest;
import com.devsouzx.accounts.handler.exceptions.*;
import com.devsouzx.accounts.service.redis.RedisService;
import com.devsouzx.accounts.service.register.IUsersRegisterService;
import com.devsouzx.accounts.util.PasswordValidatorHelper;
import com.devsouzx.accounts.util.RandomNumberUtil;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UsersRegisterServiceImpl implements IUsersRegisterService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "letterboxdclone-new-register";

    public UsersRegisterServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RedisService redisService, KafkaTemplate<String, String> kafkaTemplate) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.redisService = redisService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    @Transactional
    public void register(UserRegistrationRequest request) throws Exception {
        Optional<User> user = userRepository.findByUsername(request.getUsername());
        if (user.isPresent()) {
            throw new UsernameAlreadyExistsException("Someone has already taken that username.");
        }
        user = userRepository.findByEmail(request.getEmail());
        if (user.isPresent()) {
            throw new EmailAlreadyExistsException("That email address is already associated with an account.");
        }

        if (!PasswordValidatorHelper.isValidPassword(request.getPassword())) {
            throw new InvalidPasswordException();
        }

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
    }

    @Override
    @Transactional
    public void sendValidationEmail(String email) throws Exception {
        Optional<User> user = userRepository.findByEmail(email);

        UserConfirmationCodeResponse confirmationCodeResponse = (UserConfirmationCodeResponse) redisService.getValue(email, UserConfirmationCodeResponse.class);
        if(confirmationCodeResponse == null){
            confirmationCodeResponse = UserConfirmationCodeResponse.builder()
                    .email(email)
                    .confirmationCode(RandomNumberUtil.generateRandomCode())
                    .build();

            redisService.setValue(email, confirmationCodeResponse, TimeUnit.MILLISECONDS, 1800000L, true);
        }

        trySendKafkaMessage(confirmationCodeResponse.toString());
        log.error(confirmationCodeResponse.getConfirmationCode());
    }

    @Override
    public void validateAccount(String email, String code) throws Exception {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

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
    private void trySendKafkaMessage(String email) throws Exception {
        try {
            kafkaTemplate.send(TOPIC, email);
            log.error("Mensagem enviada com SUCESSO para o tópico: {}", TOPIC);
        } catch (Exception e) {
            log.error("Erro ao enviar mensagem para o tópico: {}", TOPIC);
        }
    }
}
