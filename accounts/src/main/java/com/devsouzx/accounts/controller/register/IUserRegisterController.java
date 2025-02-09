package com.devsouzx.accounts.controller.register;

import com.devsouzx.accounts.dto.user.AuthRequest;
import com.devsouzx.accounts.dto.user.TokenResponse;
import com.devsouzx.accounts.dto.user.UserRegistrationRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface IUserRegisterController {
    ResponseEntity<TokenResponse> register(@Valid UserRegistrationRequest request) throws Exception;
    ResponseEntity<TokenResponse> login(AuthRequest request) throws Exception;
    ResponseEntity<Void> validateAccount(UserDetails userDetails, String code) throws Exception;
}
