package com.devsouzx.Film.database.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "movies_likes")
public class MovieLikes {
    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "movieIdentifier", column = @Column(name = "movie_identifier")),
            @AttributeOverride(name = "user_identifier", column = @Column(name = "user_identifier"))
    })
    public MoviesLikesId id;

}
