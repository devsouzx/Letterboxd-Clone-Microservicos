package com.devsouzx.accounts.controller.register;

import com.devsouzx.accounts.dto.user.UserRegistrationRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface IUserRegisterController {
    ResponseEntity<Void> register(@Valid UserRegistrationRequest request) throws Exception;
    ResponseEntity<Void> sendValidationEmail(String email) throws Exception;
    ResponseEntity<Void> validateAccount(String email, String code) throws Exception;
}
