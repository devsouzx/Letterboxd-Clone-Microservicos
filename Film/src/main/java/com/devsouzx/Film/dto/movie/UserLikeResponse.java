package com.devsouzx.Film.dto.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLikeResponse {
    private UUID userId;
    private String username;
    private String avatarUrl;
    private Integer reviewId;
    private String reviewExcepted;
    private Double rating;
}
