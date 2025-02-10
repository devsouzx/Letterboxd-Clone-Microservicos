package com.devsouzx.accounts.service.settings.impl;

import com.devsouzx.accounts.database.model.User;
import com.devsouzx.accounts.database.repository.UserRepository;
import com.devsouzx.accounts.dto.user.AccountSettingsChangePasswordRequest;
import com.devsouzx.accounts.dto.user.TokenResponse;
import com.devsouzx.accounts.dto.user.UserProfileInfo;
import com.devsouzx.accounts.handler.exceptions.InvalidPasswordException;
import com.devsouzx.accounts.handler.exceptions.PasswordsNotIdenticalException;
import com.devsouzx.accounts.service.redis.RedisService;
import com.devsouzx.accounts.service.settings.IAccountSettingsService;
import com.devsouzx.accounts.util.PasswordValidatorHelper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AccountSettingsServiceImpl implements IAccountSettingsService {
    private final RedisService redisService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountSettingsServiceImpl(RedisService redisService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.redisService = redisService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public UserProfileInfo getProfileInfo(UserDetails userDetails) {
        User user = getUserByUserDetails(userDetails);

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
        User user = getUserByUserDetails(userDetails);

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

    @Override
    public void changePassword(UserDetails userDetails, AccountSettingsChangePasswordRequest request) throws Exception {
        User user = getUserByUserDetails(userDetails);

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) throw new InvalidPasswordException();

        if (!PasswordValidatorHelper.isValidPassword(request.getNewPassword())) throw new InvalidPasswordException();

        if (!request.PasswordsEquals()) throw new PasswordsNotIdenticalException();

        if (!PasswordValidatorHelper.isValidPassword(request.getConfirmNewPassword())) throw new InvalidPasswordException();

        user.setPassword(passwordEncoder.encode(request.getConfirmNewPassword()));
        userRepository.save(user);
    }

    private UserProfileInfo createUserProfileInfo(UserDetails userDetails) {
        User user = getUserByUserDetails(userDetails);

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

    public User getUserByUserDetails(UserDetails userDetails) {
        return (User) userDetails;
    }
}
