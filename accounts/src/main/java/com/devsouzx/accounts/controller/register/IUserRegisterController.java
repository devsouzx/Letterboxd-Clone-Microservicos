package com.devsouzx.accounts.controller.register;

import com.devsouzx.accounts.dto.user.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

public interface IUserRegisterController {
    ResponseEntity<TokenResponse> register(@Valid UserRegistrationRequest request) throws Exception;
    ResponseEntity<TokenResponse> login(AuthRequest request) throws Exception;
    ResponseEntity<Void> validateAccount(UserDetails userDetails, String code) throws Exception;
    ResponseEntity<Void> sendRequestPasswordResetEmail(UserRequestResetPasswordRequest request) throws Exception;
    ResponseEntity<Void> resetPassword(UUID id, String code, UserResetPasswordRequest request) throws Exception;
}
