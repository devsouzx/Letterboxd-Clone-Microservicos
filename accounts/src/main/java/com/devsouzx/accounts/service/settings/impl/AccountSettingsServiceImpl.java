package com.devsouzx.accounts.service.settings.impl;

import com.devsouzx.accounts.dto.user.*;
import com.devsouzx.accounts.service.settings.IAccountSettingsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AccountSettingsServiceImpl implements IAccountSettingsService {

    @Override
    public UserProfileInfo getProfileInfo(UserDetails userDetails) {
        // Implementar lógica para buscar perfil do usuário
        return new UserProfileInfo();
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
