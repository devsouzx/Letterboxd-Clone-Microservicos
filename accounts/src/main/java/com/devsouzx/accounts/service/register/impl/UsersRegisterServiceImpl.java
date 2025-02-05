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
    private static final String TOPIC = "twitterclone-new-register";
    private static final Integer PAUSE_TIME = 15000;
    private static final Integer LIMIT_TIME = 300;

    public UsersRegisterServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RedisService redisService, KafkaTemplate<String, String> kafkaTemplate) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.redisService = redisService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void register(UserRegistrationRequest request) throws Exception {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(UsernameAlreadyExistsException::new);
        user = userRepository.findByEmail(request.getEmail()).orElseThrow(EmailAlreadyExistsException::new);

        if (!PasswordValidatorHelper.isValidPassword(request.getPassword())) {
            throw new InvalidPasswordException();
        }

        userRepository.save(User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .username(request.getUsername())
                .registrationTime(LocalDateTime.now())
                .firstAccess(true)
                .build());

        sendValidationEmail(request.getEmail());
    }

    @Override
    public void sendValidationEmail(String email) throws Exception {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            throw new UsernameAlreadyExistsException();
        }

        UserConfirmationCodeResponse confirmationCodeResponse = (UserConfirmationCodeResponse) redisService.getValue(email, UserConfirmationCodeResponse.class);
        if(confirmationCodeResponse == null){
            confirmationCodeResponse = UserConfirmationCodeResponse.builder()
                    .email(email)
                    .confirmationCode(RandomNumberUtil.generateRandomCode())
                    .build();

            redisService.setValue(email, confirmationCodeResponse, TimeUnit.MILLISECONDS, 1800000L, true);
        }

        trySendKafkaMessage(confirmationCodeResponse.toString());
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

    private void trySendKafkaMessage(String email) throws Exception {
        boolean notSent = true;
        int waitingTime = 0;

        do {
            try {
                kafkaTemplate.send(TOPIC, email);
                notSent = false;
                log.error("Mensagem enviada com SUCESSO para o tópico: {}", TOPIC);
            } catch (Exception e) {
                log.error("Erro ao enviar mensagem para o tópico: {}", TOPIC);
                Thread.sleep(PAUSE_TIME);
                waitingTime += 15;
            }
        } while (notSent && waitingTime <= LIMIT_TIME);
    }
}
