package com.devsouzx.Film.dto.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TMDbFilmTranslationsResponse {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("translations")
    private List<Translation> translations;

    @Data
    public static class Translation {
        @JsonProperty("iso_3166_1")
        private String iso31661;

        @JsonProperty("iso_639_1")
        private String iso6391;

        @JsonProperty("name")
        private String name;

        @JsonProperty("english_name")
        private String englishName;

        @JsonProperty("data")
        private DataObject data;
    }

    @Data
    public static class DataObject {
        @JsonProperty("homepage")
        private String homepage;

        @JsonProperty("overview")
        private String overview;

        @JsonProperty("runtime")
        private Integer runtime;

        @JsonProperty("tagline")
        private String tagline;

        @JsonProperty("title")
        private String title;
    }
}
