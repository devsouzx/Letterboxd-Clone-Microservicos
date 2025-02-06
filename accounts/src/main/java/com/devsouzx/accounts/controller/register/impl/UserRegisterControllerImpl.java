package com.devsouzx.accounts.controller.register.impl;

import com.devsouzx.accounts.dto.user.UserRegistrationRequest;
import com.devsouzx.accounts.service.register.IUsersRegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1")
public class UserRegisterControllerImpl {
    private final IUsersRegisterService iUsersRegisterService;

    public UserRegisterControllerImpl(IUsersRegisterService iUsersRegisterService) {
        this.iUsersRegisterService = iUsersRegisterService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Void> register(@RequestBody UserRegistrationRequest request) throws Exception {
        iUsersRegisterService.register(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/user/validate.do/{code}")
    public ResponseEntity<Void> confirmCode(@RequestBody String email,
                                             @PathVariable("code") String code) throws Exception {
        iUsersRegisterService.validateAccount(email, code);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
