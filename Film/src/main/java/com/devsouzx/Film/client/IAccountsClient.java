package com.devsouzx.Film.client;

import com.devsouzx.Film.dto.user.UserProfileInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "accounts", configuration = FeignClient.class)
public interface IAccountsClient {
    @GetMapping("/api/v1/settings")
    UserProfileInfo getProfileInfo();
}
