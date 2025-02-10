package com.devsouzx.accounts.service.settings.impl;

import com.devsouzx.accounts.database.model.User;
import com.devsouzx.accounts.database.repository.UserRepository;
import com.devsouzx.accounts.dto.user.UserProfileInfo;
import com.devsouzx.accounts.service.redis.RedisService;
import com.devsouzx.accounts.service.settings.IAccountSettingsService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class AccountSettingsServiceImpl implements IAccountSettingsService {
    private final RedisService redisService;
    private final UserRepository userRepository;

    public AccountSettingsServiceImpl(RedisService redisService, UserRepository userRepository) {
        this.redisService = redisService;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserProfileInfo getProfileInfo(UserDetails userDetails) {
        User user = (User) userDetails;

        UserProfileInfo userProfileInfo = (UserProfileInfo) redisService.getValue("PROFILEINFO_" + user.getIdentifier(), UserProfileInfo.class);
        if (userProfileInfo == null) {
            userProfileInfo = createUserProfileInfo(user);

            redisService.setValue("PROFILEINFO_" + user.getIdentifier(), userProfileInfo, TimeUnit.MILLISECONDS, 1800000L, true);
        }
        return userProfileInfo;
    }

    @Transactional
    @Override
    public UserProfileInfo updateProfileInfo(UserDetails userDetails, UserProfileInfo request) {
        User user = (User) userDetails;

        user.setUsername(request.getUsername());
        user.setFirstname(request.getGivenName());
        user.setLastname(request.getFamilyName());
        user.setEmail(request.getEmailAddress());
        user.setLocation(request.getLocation());
        user.setSite(request.getWebsite());
        user.setBiography(request.getBio());
        user.setPronoun(request.getPronoun());
        user.setCustomPosters(request.getCustomPosters());
        user.setIncludeProfileInMembers(request.getIncludeProfileInMembers());
        user.setAdultContent(request.getAdultContent());
        user.setReplies(request.getReplies());

        userRepository.save(user);

        UserProfileInfo userProfileInfo = (UserProfileInfo) redisService.getValue("PROFILEINFO_" + user.getIdentifier(), UserProfileInfo.class);

        if (userProfileInfo != null) {
            redisService.removeKey("PROFILEINFO_" + user.getIdentifier());
        }

        userProfileInfo = createUserProfileInfo(userDetails);

        redisService.setValue("PROFILEINFO_" + user.getIdentifier(), userProfileInfo, TimeUnit.MILLISECONDS, 1800000L, true);

        return userProfileInfo;
    }

    private UserProfileInfo createUserProfileInfo(UserDetails userDetails) {
        User user = (User) userDetails;
        return UserProfileInfo.builder()
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
    }
}
