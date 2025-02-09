package com.devsouzx.accounts.handler.exceptions;

import com.devsouzx.accounts.enums.InternalTypeErrorCodesEnum;

public class NotConfirmedEmailException extends ErrorCodeException{

    public NotConfirmedEmailException() {
        super(InternalTypeErrorCodesEnum.E410002);
    }

    public NotConfirmedEmailException(String message) {
        super(InternalTypeErrorCodesEnum.E410002, message);
    }
}