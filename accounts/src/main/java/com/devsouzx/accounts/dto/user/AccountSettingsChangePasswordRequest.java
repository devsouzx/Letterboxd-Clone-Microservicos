package com.devsouzx.accounts.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountSettingsChangePasswordRequest {
    @NotNull
    @NotBlank
    private String currentPassword;
    @NotNull
    @NotBlank
    private String newPassword;
    @NotNull
    @NotBlank
    private String confirmNewPassword;

    public Boolean PasswordsEquals() {
        return newPassword.equals(confirmNewPassword);
    }
}
