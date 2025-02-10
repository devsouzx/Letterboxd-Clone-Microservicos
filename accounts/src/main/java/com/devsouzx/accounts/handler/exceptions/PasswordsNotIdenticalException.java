package com.devsouzx.accounts.handler.exceptions;

import com.devsouzx.accounts.enums.InternalTypeErrorCodesEnum;

public class PasswordsNotIdenticalException extends ErrorCodeException{
    public PasswordsNotIdenticalException(){
        super(InternalTypeErrorCodesEnum.E410007);
    }

    public PasswordsNotIdenticalException(String message){
        super(InternalTypeErrorCodesEnum.E410007, message);
    }
}

