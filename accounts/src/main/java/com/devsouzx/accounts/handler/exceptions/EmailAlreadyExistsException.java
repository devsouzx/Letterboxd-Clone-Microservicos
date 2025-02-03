package com.devsouzx.accounts.handler.exceptions;

import com.devsouzx.accounts.enums.InternalTypeErrorCodesEnum;

public class EmailAlreadyExistsException extends ErrorCodeException {
    public EmailAlreadyExistsException(){
        super(InternalTypeErrorCodesEnum.E410002);
    }

    public EmailAlreadyExistsException(String message){
        super(InternalTypeErrorCodesEnum.E410002, message);
    }
}
