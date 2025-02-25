package com.devsouzx.Film.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "tmdbClient", url = "https://api.themoviedb.org/3")
public interface ITMDBClient {
    @GetMapping(value = "/movie/popular")
    TMDBResponse getPopularMovies(@RequestParam("api_key") String apiKey);
}
