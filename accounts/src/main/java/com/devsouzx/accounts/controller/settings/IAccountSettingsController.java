package com.devsouzx.accounts.controller.settings;

import com.devsouzx.accounts.dto.user.AccountSettingsChangePasswordRequest;
import com.devsouzx.accounts.dto.user.UserProfileInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface IAccountSettingsController {
    ResponseEntity<UserProfileInfo> getProfileInfo(UserDetails userDetails) throws Exception;
    ResponseEntity<UserProfileInfo> updateProfileInfo(UserDetails userDetails, UserProfileInfo request) throws Exception;
    ResponseEntity<Void> changePassword(UserDetails userDetails, AccountSettingsChangePasswordRequest request) throws Exception;
}
