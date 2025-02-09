package com.devsouzx.accounts.service.register;

import com.devsouzx.accounts.dto.user.AuthRequest;
import com.devsouzx.accounts.dto.user.TokenResponse;
import com.devsouzx.accounts.dto.user.UserRegistrationRequest;

public interface IUsersRegisterService {
    TokenResponse register(UserRegistrationRequest request) throws Exception;
    TokenResponse authenticate(AuthRequest request, Boolean login) throws Exception;
    void sendValidationEmail(String email) throws Exception;
    void validateAccount(String email, String code) throws Exception;
}
