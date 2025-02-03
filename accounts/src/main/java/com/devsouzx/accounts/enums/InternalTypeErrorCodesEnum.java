package com.devsouzx.accounts.enums;

public enum InternalTypeErrorCodesEnum {
    E410000("410.000", "User not found."),
    E410001("410.001", "Username already exists."),
    E410002("410.002", "Email already exists."),
    E410003("410.003", "This email is already confirmed."),
    E410004("410.004", "Confirmation code not matches."),
    E410005("410.005", "Your password must have at least 8 characters");

    private final String code;
    private final String message;

    InternalTypeErrorCodesEnum(String code, String message) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
