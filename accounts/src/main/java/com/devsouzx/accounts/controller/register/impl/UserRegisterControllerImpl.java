package com.devsouzx.accounts.controller.register.impl;

import com.devsouzx.accounts.controller.register.IUserRegisterController;
import com.devsouzx.accounts.database.model.User;
import com.devsouzx.accounts.dto.user.AuthRequest;
import com.devsouzx.accounts.dto.user.TokenResponse;
import com.devsouzx.accounts.dto.user.UserRegistrationRequest;
import com.devsouzx.accounts.dto.user.UserResetPasswordRequest;
import com.devsouzx.accounts.service.auth.IUsersAuthenticationService;
import com.devsouzx.accounts.service.redis.RedisService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1")
public class UserRegisterControllerImpl implements IUserRegisterController {
    private final IUsersAuthenticationService iUsersAuthenticationService;
    private RedisService redisService;

    public UserRegisterControllerImpl(IUsersAuthenticationService iUsersAuthenticationService) {
        this.iUsersAuthenticationService = iUsersAuthenticationService;
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
    public ResponseEntity<Void> sendRequestPasswordResetEmail(@RequestBody UserResetPasswordRequest request) throws Exception {
        iUsersAuthenticationService.sendPasswordResetEmail(request.getEmail());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
