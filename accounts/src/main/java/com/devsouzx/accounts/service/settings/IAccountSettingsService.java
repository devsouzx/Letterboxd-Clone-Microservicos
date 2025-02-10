package com.devsouzx.accounts.service.settings;

import com.devsouzx.accounts.dto.user.UserProfileInfo;
import org.springframework.security.core.userdetails.UserDetails;

public interface IAccountSettingsService {
    UserProfileInfo getProfileInfo(UserDetails userDetails);
}
