package com.devsouzx.accounts.service.settings;

import com.devsouzx.accounts.dto.user.AccountSettingsChangePasswordRequest;
import com.devsouzx.accounts.dto.user.UserProfileInfo;
import org.springframework.security.core.userdetails.UserDetails;

public interface IAccountSettingsService {
    UserProfileInfo getProfileInfo(UserDetails userDetails);
    UserProfileInfo updateProfileInfo(UserDetails userDetails, UserProfileInfo request);
    void changePassword(UserDetails userDetails, AccountSettingsChangePasswordRequest request) throws Exception;
}
