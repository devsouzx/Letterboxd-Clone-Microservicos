package com.devsouzx.accounts.service.settings.impl;

import com.devsouzx.accounts.database.model.User;
import com.devsouzx.accounts.dto.user.UserProfileInfo;
import com.devsouzx.accounts.service.redis.RedisService;
import com.devsouzx.accounts.service.settings.IAccountSettingsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class AccountSettingsServiceImpl implements IAccountSettingsService {
    public final RedisService redisService;

    public AccountSettingsServiceImpl(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public UserProfileInfo getProfileInfo(UserDetails userDetails) {
        User user = (User) userDetails;

        UserProfileInfo userProfileInfo = (UserProfileInfo) redisService.getValue("PROFILEINFO_" + user.getIdentifier(), UserProfileInfo.class);
        if (userProfileInfo == null) {
            userProfileInfo = UserProfileInfo.builder()
                    .username(user.getUsername())
                    .givenName(user.getFirstname())
                    .familyName(user.getLastname())
                    .emailAddress(user.getEmail())
                    .location(user.getLocation())
                    .website(user.getSite())
                    .bio(user.getBiography())
                    .pronoun(user.getPronoun())
                    .customPosters(user.getCustomPosters())
                    .includeProfileInMembers(user.getIncludeProfileInMembers())
                    .adultContent(user.getAdultContent())
                    .replies(user.getReplies())
                    .build();

            redisService.setValue("PROFILEINFO_" + user.getIdentifier(), userProfileInfo, TimeUnit.MILLISECONDS, 1800000L, true);
        }
        return userProfileInfo;
    }
}
