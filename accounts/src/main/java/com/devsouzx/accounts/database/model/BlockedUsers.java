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
@Table(name = "blocked_users")
public class BlockedUsers {
    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "blockerIdentifier", column = @Column(name = "blocker_identifier")),
            @AttributeOverride(name = "blockedIdentifier", column = @Column(name = "blocked_identifier")),
    })
    private BlockedUsersId id;
}
