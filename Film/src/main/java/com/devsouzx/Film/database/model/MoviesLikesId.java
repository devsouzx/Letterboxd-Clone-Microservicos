package com.devsouzx.Film.database.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class MoviesLikesId {
    @Column(name = "movie_identifier")
    private UUID movieIdentifier;

    @Column(name = "user_identifier")
    private UUID userIdentifier;
}
