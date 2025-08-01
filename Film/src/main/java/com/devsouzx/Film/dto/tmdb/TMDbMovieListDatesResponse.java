package com.devsouzx.Film.dto.tmdb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TMDbMovieListDatesResponse extends TMDbMovieListResponse {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Dates {
        @JsonProperty("maximum")
        private String maximum;
        @JsonProperty("minimum")
        private String minimum;
    }

    @JsonProperty("dates")
    private Dates dates;
}
