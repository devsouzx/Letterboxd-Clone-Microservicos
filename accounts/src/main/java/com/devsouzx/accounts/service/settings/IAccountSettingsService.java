package com.devsouzx.accounts.service.settings;

import com.devsouzx.accounts.database.model.User;
import com.devsouzx.accounts.dto.user.AccountSettingsChangePasswordRequest;
import com.devsouzx.accounts.dto.user.AvatarUrlResponse;
import com.devsouzx.accounts.dto.user.UserProfileInfo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

public interface IAccountSettingsService {
    User FindUserByUsernameOrEmailOrIdentifier(String targetUserIdentifier);
    UserProfileInfo getProfileInfo(String sessionUserIdentifier);
    UserProfileInfo updateProfileInfo(UserDetails userDetails, UserProfileInfo request);
    void changePassword(UserDetails userDetails, AccountSettingsChangePasswordRequest request) throws Exception;
    String uploadImage(MultipartFile multipartFile);
    void updateAvatar(UserDetails userDetails, MultipartFile avatar);
    AvatarUrlResponse getUserAvatar(UserDetails userDetails);
}
