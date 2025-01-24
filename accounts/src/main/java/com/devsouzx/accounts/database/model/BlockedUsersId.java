package com.devsouzx.accounts.database.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class BlockedUsersId {
    @Column(name = "blocker_identifier")
    private String blockerIdentifier;

    @Column(name = "blocked_identifier")
    private String blockedIdentifier;
}
