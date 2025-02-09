package com.devsouzx.accounts.service.auth;

import com.devsouzx.accounts.dto.user.*;
import jakarta.transaction.Transactional;

import java.util.UUID;

public interface IUsersAuthenticationService {
    TokenResponse register(UserRegistrationRequest request) throws Exception;
    TokenResponse authenticate(AuthRequest request, Boolean login) throws Exception;
    void sendValidationEmail(String email) throws Exception;
    void sendPasswordResetEmail(String email) throws Exception;
    void resetPassword(UserResetPasswordRequest request, UUID id, String code) throws Exception;
    void validateAccount(String email, String code) throws Exception;
}
