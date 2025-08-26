package com.devsouzx.Film.client;

import com.devsouzx.Film.configuration.feign.FeignConfiguration;
import com.devsouzx.Film.dto.user.UserProfileInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "localhost:8087", configuration = FeignConfiguration.class)
public interface IAccountsClient {
    @GetMapping("/api/v1/accounts/settings/{userIdentifier}")
    UserProfileInfo getProfileInfoByIdentifier(@PathVariable("userIdentifier") String identifier);
}
