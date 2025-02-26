package com.devsouzx.Film.dto.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TMDbAlternativeTitlesResponse {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("titles")
    private List<Titles> titles;

    @Data
    public static class Titles {
        @JsonProperty("iso_3166_1")
        private String code;
        @JsonProperty("title")
        private String title;
        @JsonProperty("type")
        private String type;
    }
}
