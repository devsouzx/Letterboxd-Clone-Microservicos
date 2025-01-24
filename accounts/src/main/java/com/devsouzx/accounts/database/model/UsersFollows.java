package com.devsouzx.accounts.database.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "users_follows")
public class UsersFollows {
    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "followerIdentifier", column = @Column(name = "follower_identifier")),
            @AttributeOverride(name = "followedIdentifier", column = @Column(name = "followed_identifier")),
    })
    private UsersFollowsId id;
}
