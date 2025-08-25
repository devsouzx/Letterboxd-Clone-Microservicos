package com.devsouzx.accounts.service.settings.impl;

import com.devsouzx.accounts.database.model.User;
import com.devsouzx.accounts.database.repository.UserRepository;
import com.devsouzx.accounts.dto.user.*;
import com.devsouzx.accounts.service.settings.IAccountSettingsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Service
public class AccountSettingsServiceImpl implements IAccountSettingsService {
    private final UserRepository userRepository;

    public AccountSettingsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User FindUserByUsernameOrEmailOrIdentifier(String targetUserIdentifier) {
        Optional<User> user;

        user = userRepository.findByUsername(targetUserIdentifier);
        if (user.isEmpty()) {
            user = userRepository.findByEmail(targetUserIdentifier);
        }
        if (user.isEmpty()) {
            user = userRepository.findById(UUID.fromString(targetUserIdentifier));
        }

        return user.get();
    }

    @Override
    public UserProfileInfo getProfileInfo(String sessionUserIdentifier) {
        User user = FindUserByUsernameOrEmailOrIdentifier(sessionUserIdentifier);

        return UserProfileInfo.builder()
                .identifier(user.getIdentifier())
                .username(user.getUsername())
                .givenName(user.getFirstname())
                .familyName(user.getLastname())
                .emailAddress(user.getEmail())
                .bio(user.getBiography())
                .location(user.getLocation())
                .website(user.getSite())
                .pronoun(user.getPronoun())
                .adultContent(user.getAdultContent())
                .customPosters(user.getCustomPosters())
                .replies(user.getReplies())
                .avatarUrl(user,getUserAvatar())
                .includeProfileInMembers(user.getIncludeProfileInMembers())
                .build();
    }

    @Override
    public UserProfileInfo updateProfileInfo(UserDetails userDetails, UserProfileInfo request) {
        // Implementar lógica para atualizar perfil do usuário
        return request;
    }

    @Override
    public void changePassword(UserDetails userDetails, AccountSettingsChangePasswordRequest request) throws Exception {
        // Implementar lógica para alteração de senha
    }

    @Override
    public String uploadImage(MultipartFile multipartFile) {
        // Implementar lógica para upload de imagem e retornar URL
        return null;
    }

    @Override
    public void updateAvatar(UserDetails userDetails, MultipartFile avatar) {
        // Implementar lógica para atualizar avatar do usuário
    }

    @Override
    public AvatarUrlResponse getUserAvatar(UserDetails userDetails) {
        // Implementar lógica para buscar URL do avatar do usuário
        return new AvatarUrlResponse();
    }
}
