package com.devsouzx.accounts.controller.register.impl;

import com.devsouzx.accounts.controller.register.IUserRegisterController;
import com.devsouzx.accounts.database.model.User;
import com.devsouzx.accounts.dto.user.*;
import com.devsouzx.accounts.service.auth.IUsersAuthenticationService;
import com.devsouzx.accounts.service.redis.RedisService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/accounts")
public class UserRegisterControllerImpl implements IUserRegisterController {
    private final IUsersAuthenticationService iUsersAuthenticationService;
    private final RedisService redisService;

    public UserRegisterControllerImpl(IUsersAuthenticationService iUsersAuthenticationService, RedisService redisService) {
        this.iUsersAuthenticationService = iUsersAuthenticationService;
        this.redisService = redisService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<TokenResponse> register(@RequestBody UserRegistrationRequest request) throws Exception {
        return ResponseEntity.ok(iUsersAuthenticationService.register(request));
    }

    @PostMapping(value = "/sign-in")
    public ResponseEntity<TokenResponse> login(@RequestBody AuthRequest request) throws Exception {
        return ResponseEntity.ok(iUsersAuthenticationService.authenticate(request, request.getRememberMe()));
    }

    @GetMapping(value = "/user/validate.do/{code}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> validateAccount(@AuthenticationPrincipal UserDetails userDetails,
                                            @PathVariable("code") String code) throws Exception {
        String email = ((User) userDetails).getEmail();
        redisService.isValidUser(userDetails);
        iUsersAuthenticationService.validateAccount(email, code);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/user/request-password-reset/")
    public ResponseEntity<Void> sendRequestPasswordResetEmail(@RequestBody UserRequestResetPasswordRequest request) throws Exception {
        iUsersAuthenticationService.sendPasswordResetEmail(request.getEmail());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/user/resetpassword/")
    public ResponseEntity<Void> resetPassword(@RequestParam("id") UUID id,
                                              @RequestParam("hash") String code,
                                              @RequestBody UserResetPasswordRequest request) throws Exception {
        iUsersAuthenticationService.resetPassword(request, id, code);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
