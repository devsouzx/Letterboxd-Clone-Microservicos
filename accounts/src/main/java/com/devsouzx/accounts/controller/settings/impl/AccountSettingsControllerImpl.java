package com.devsouzx.accounts.controller.settings.impl;

import com.devsouzx.accounts.controller.settings.IAccountSettingsController;
import com.devsouzx.accounts.dto.user.AccountSettingsChangePasswordRequest;
import com.devsouzx.accounts.dto.user.AvatarUrlResponse;
import com.devsouzx.accounts.dto.user.UserProfileInfo;
import com.devsouzx.accounts.service.redis.RedisService;
import com.devsouzx.accounts.service.settings.IAccountSettingsService;
import com.devsouzx.accounts.util.FindUserIdentifierHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/settings")
public class AccountSettingsControllerImpl implements IAccountSettingsController {
    private final IAccountSettingsService iAccountSettingsService;
    private final RedisService redisService;

    public AccountSettingsControllerImpl(IAccountSettingsService iAccountSettingsService, RedisService redisService) {
        this.iAccountSettingsService = iAccountSettingsService;
        this.redisService = redisService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserProfileInfo> getProfileInfo() throws Exception {
        String sessionUserIdentifier = FindUserIdentifierHelper.getIdentifier();
        return ResponseEntity.ok(iAccountSettingsService.getProfileInfo(sessionUserIdentifier));
    }

    @PutMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserProfileInfo> updateProfileInfo(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UserProfileInfo request) throws Exception {
        redisService.isValidUser(userDetails);
        return ResponseEntity.ok(iAccountSettingsService.updateProfileInfo(userDetails, request));
    }

    @PutMapping(value = "/auth")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> changePassword(@AuthenticationPrincipal UserDetails userDetails, @RequestBody AccountSettingsChangePasswordRequest request) throws Exception {
        redisService.isValidUser(userDetails);
        iAccountSettingsService.changePassword(userDetails, request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/avatar", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadUserAvatar(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("avatar")MultipartFile avatar) throws Exception {
        iAccountSettingsService.updateAvatar(userDetails, avatar);
        redisService.isValidUser(userDetails);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/avatar")
    public ResponseEntity<AvatarUrlResponse> getUserAvatar(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(iAccountSettingsService.getUserAvatar(userDetails));
    }
}
