package com.devsouzx.accounts.handler.exceptions;

import com.devsouzx.accounts.enums.InternalTypeErrorCodesEnum;

public class ConfirmationCodeNotMatchesException extends ErrorCodeException {
    public ConfirmationCodeNotMatchesException(){
        super(InternalTypeErrorCodesEnum.E410004);
    }

    public ConfirmationCodeNotMatchesException(String message){
        super(InternalTypeErrorCodesEnum.E410004, message);
    }
}
