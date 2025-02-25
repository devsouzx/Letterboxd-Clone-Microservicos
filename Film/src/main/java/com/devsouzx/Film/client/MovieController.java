package com.devsouzx.Film.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {
    @Autowired
    private ITMDBClient movieService;

    @GetMapping("/popular")
    public ResponseEntity<TMDBResponse> getPopularMovies() {
        return ResponseEntity.ok(movieService.getPopularMovies(""));
    }
}
