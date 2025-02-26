package com.devsouzx.Film.dto.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TMDbFilmWatchProvidersResponse {
    @JsonProperty("logo_path")
    private Integer id;

    @JsonProperty("results")
    private Map<String, CountryWatchProviders> results;

    @Data
    public static class CountryWatchProviders {
        @JsonProperty("link")
        private String link;

        @JsonProperty("flatrate")
        private List<ProviderInfo> flatrate;

        @JsonProperty("rent")
        private List<ProviderInfo> rent;

        @JsonProperty("buy")
        private List<ProviderInfo> buy;
    }

    @Data
    public static class ProviderInfo {
        @JsonProperty("logo_path")
        private String logoPath;

        @JsonProperty("provider_id")
        private int providerId;

        @JsonProperty("provider_name")
        private String providerName;

        @JsonProperty("display_priority")
        private int displayPriority;
    }
}
