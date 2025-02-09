package com.devsouzx.accounts.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResetPasswordResponse {
    private String email;
    private String resetPasswordCode;

    @Override
    public String toString() {
        return "{\"email\": \"" + email + "\", \"resetPasswordCode\": \"" + resetPasswordCode + "\"}";
    }
}
