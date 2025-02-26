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
public class TMDbFilmVideosResponse {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("results")
    private List<Video> results;

    @Data
    public static class Video {
        @JsonProperty("iso_639_1")
        private String iso6391;
        @JsonProperty("iso_3166_1")
        private String iso31661;
        @JsonProperty("name")
        private String name;
        @JsonProperty("key")
        private String key;
        @JsonProperty("site")
        private String site;
        @JsonProperty("size")
        private Integer size;
        @JsonProperty("type")
        private String type;
        @JsonProperty("official")
        private Boolean official;
        @JsonProperty("published_at")
        private String publishedAt;
        @JsonProperty("id")
        private String id;
    }
}
