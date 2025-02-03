package com.devsouzx.accounts.handler.exceptions;

import com.devsouzx.accounts.enums.InternalTypeErrorCodesEnum;

public class InvalidPasswordException extends ErrorCodeException{
    public InvalidPasswordException(){
        super(InternalTypeErrorCodesEnum.E410005);
    }

    public InvalidPasswordException(String message){
        super(InternalTypeErrorCodesEnum.E410005, message);
    }
}
