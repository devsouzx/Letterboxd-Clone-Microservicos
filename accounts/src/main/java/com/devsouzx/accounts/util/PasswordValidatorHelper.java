package com.devsouzx.accounts.util;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PasswordValidatorHelper {
    public static boolean isValidPassword(String password) {
        return password.length() >= 8 && password.length() <= 15;
    }
}
