package com.devsouzx.accounts.service.register;

import com.devsouzx.accounts.dto.user.UserRegistrationRequest;

public interface IUsersRegisterService {
    String register(UserRegistrationRequest request) throws Exception;
    void sendValidationEmail(String email) throws Exception;
    void validateAccount(String email, String code) throws Exception;
}
