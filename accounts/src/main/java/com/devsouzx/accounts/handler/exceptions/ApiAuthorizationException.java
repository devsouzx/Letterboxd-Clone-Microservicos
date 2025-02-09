package com.devsouzx.accounts.handler.exceptions;

import com.devsouzx.accounts.enums.InternalTypeErrorCodesEnum;

public class ApiAuthorizationException extends ErrorCodeException{
    public ApiAuthorizationException(){
        super(InternalTypeErrorCodesEnum.E410002);
    }

    public ApiAuthorizationException(String message){
        super(InternalTypeErrorCodesEnum.E410002, message);
    }
}
