package com.devsouzx.accounts.controller.settings;

import com.devsouzx.accounts.dto.user.AccountSettingsChangePasswordRequest;
import com.devsouzx.accounts.dto.user.AvatarUrlResponse;
import com.devsouzx.accounts.dto.user.UserProfileInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface IAccountSettingsController {
    ResponseEntity<UserProfileInfo> getProfileInfo() throws Exception;
    ResponseEntity<UserProfileInfo> updateProfileInfo(UserDetails userDetails, UserProfileInfo request) throws Exception;
    ResponseEntity<Void> changePassword(UserDetails userDetails, AccountSettingsChangePasswordRequest request) throws Exception;
    ResponseEntity<String> uploadUserAvatar(UserDetails userDetails, MultipartFile avatar) throws Exception;
    ResponseEntity<AvatarUrlResponse> getUserAvatar(UserDetails userDetails);
}
