package com.devsouzx.accounts.handler.exceptions;

import com.devsouzx.accounts.enums.InternalTypeErrorCodesEnum;

public class WrongPasswordException extends ErrorCodeException{
    public WrongPasswordException(){
        super(InternalTypeErrorCodesEnum.E410006);
    }

    public WrongPasswordException(String message){
        super(InternalTypeErrorCodesEnum.E410006, message);
    }
}

