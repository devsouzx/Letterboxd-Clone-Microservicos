package com.devsouzx.Film.dto.movie;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieLikesResponse {
    private UUID movieId;
    private String movieTitle;
    private String movieReleaseYear;
    private Integer totalLikes;
    private List<UserLikeResponse> likes;
}
