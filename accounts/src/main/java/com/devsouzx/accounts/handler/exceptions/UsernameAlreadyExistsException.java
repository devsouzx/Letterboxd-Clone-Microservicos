package com.devsouzx.accounts.handler.exceptions;

import com.devsouzx.accounts.enums.InternalTypeErrorCodesEnum;

public class UsernameAlreadyExistsException extends ErrorCodeException {
    public UsernameAlreadyExistsException(){
        super(InternalTypeErrorCodesEnum.E410001);
    }

    public UsernameAlreadyExistsException(String message){
        super(InternalTypeErrorCodesEnum.E410001, message);
    }
}