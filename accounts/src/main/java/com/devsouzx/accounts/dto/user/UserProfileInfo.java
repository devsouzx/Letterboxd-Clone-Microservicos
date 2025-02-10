package com.devsouzx.accounts.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileInfo {
    private String username;
    private String givenName;
    private String familyName;
    private String emailAddress;
    private String location;
    private String website;
    private String bio;
    private String pronoun;
    private Boolean customPosters;
    private String replies;
    private Boolean includeProfileInMembers;
    private Boolean adultContent;
}
