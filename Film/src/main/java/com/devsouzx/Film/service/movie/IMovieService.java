package com.devsouzx.Film.service.movie;

import com.devsouzx.Film.dto.movie.CrewMemberResponse;
import com.devsouzx.Film.dto.movie.MovieLikesResponse;
import com.devsouzx.Film.dto.movie.MovieResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface IMovieService {
    public MovieResponse getMovieByTmdbID(Integer tmdbID);
    public MovieResponse getOrSaveMovieByTmdbID(Integer tmdbID);
    public MovieResponse saveFromTmdb(Integer tmdbID);
    MovieResponse updateFromTmdb(UUID identifier);
    MovieResponse getMovieBySlug(String slug);
    List<CrewMemberResponse> getMovieCrewBySlug(String slug);
    MovieLikesResponse getMovieLikesBySlug(String slug) throws Exception;
    void likeOrUnlikeMovieBySlug(String slug) throws Exception;
}
