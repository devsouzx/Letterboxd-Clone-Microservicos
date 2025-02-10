package com.devsouzx.accounts.controller.settings.impl;

import com.devsouzx.accounts.controller.settings.IAccountSettingsController;
import com.devsouzx.accounts.dto.user.UserProfileInfo;
import com.devsouzx.accounts.service.settings.IAccountSettingsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/settings")
public class AccountSettingsControllerImpl implements IAccountSettingsController {
    private final IAccountSettingsService iAccountSettingsService;

    public AccountSettingsControllerImpl(IAccountSettingsService iAccountSettingsService) {
        this.iAccountSettingsService = iAccountSettingsService;
    }

    @GetMapping
    public ResponseEntity<UserProfileInfo> getProfileInfo(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(iAccountSettingsService.getProfileInfo(userDetails));
    }
}
