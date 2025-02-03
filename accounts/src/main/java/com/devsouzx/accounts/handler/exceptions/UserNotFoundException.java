package com.devsouzx.accounts.handler.exceptions;

import com.devsouzx.accounts.enums.InternalTypeErrorCodesEnum;

public class UserNotFoundException extends ErrorCodeException {
    public UserNotFoundException(){
        super(InternalTypeErrorCodesEnum.E410000);
    }

    public UserNotFoundException(String message){
        super(InternalTypeErrorCodesEnum.E410000, message);
    }
}
