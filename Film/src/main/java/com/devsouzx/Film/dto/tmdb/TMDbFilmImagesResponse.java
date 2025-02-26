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
public class TMDbFilmImagesResponse {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("backdrops")
    private List<ImageData> backdrops;

    @JsonProperty("logos")
    private List<ImageData> logos;

    @JsonProperty("posters")
    private List<ImageData> posters;

    public static class ImageData {
        @JsonProperty("aspect_ratio")
        private Double aspectRatio;

        @JsonProperty("height")
        private Integer height;

        @JsonProperty("iso_639_1")
        private String code;

        @JsonProperty("file_path")
        private String filePath;

        @JsonProperty("vote_average")
        private Double voteAverage;

        @JsonProperty("vote_count")
        private Integer voteCount;

        @JsonProperty("width")
        private Integer width;
    }
}
