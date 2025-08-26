package com.devsouzx.Film.service.movie.impl;

import com.devsouzx.Film.client.IAccountsClient;
import com.devsouzx.Film.client.ITMDbClient;
import com.devsouzx.Film.database.model.Movie;
import com.devsouzx.Film.database.model.MovieLikes;
import com.devsouzx.Film.database.model.MoviesLikesId;
import com.devsouzx.Film.dto.movie.CrewMemberResponse;
import com.devsouzx.Film.dto.movie.MovieLikesResponse;
import com.devsouzx.Film.dto.movie.MovieResponse;
import com.devsouzx.Film.dto.movie.UserLikeResponse;
import com.devsouzx.Film.dto.tmdb.TMDbFilmResponse;
import com.devsouzx.Film.dto.user.UserProfileInfo;
import com.devsouzx.Film.mapper.FilmMapper;
import com.devsouzx.Film.repository.movie.MovieLikesRepository;
import com.devsouzx.Film.repository.movie.MovieRepository;
import com.devsouzx.Film.service.movie.IMovieService;
import com.devsouzx.Film.util.FindUserIdentifierHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MovieService implements IMovieService {
    @Value("${tmdb.apiKey}")
    private String apiKey;
    private final ITMDbClient client;
    private final FilmMapper mapper;
    private final MovieRepository movieRepository;
    private final MovieLikesRepository movieLikesRepository;
    private final IAccountsClient iAccountsClient;

    public MovieService(FilmMapper mapper, ITMDbClient client, MovieRepository movieRepository, MovieLikesRepository movieLikesRepository, IAccountsClient iAccountsClient) {
        this.mapper = mapper;
        this.client = client;
        this.movieRepository = movieRepository;
        this.movieLikesRepository = movieLikesRepository;
        this.iAccountsClient = iAccountsClient;
    }

    public MovieResponse getMovieByTmdbID(Integer tmdbID) {
        TMDbFilmResponse movieResponse = client.getMovieDetailsById(apiKey, tmdbID);
        return mapper.toMovieResponseFromTmdb(movieResponse);
    }

    public MovieResponse getOrSaveMovieByTmdbID(Integer tmdbID) {
        Optional<Movie> optional = movieRepository.getByTmdbID(tmdbID);

        if (optional.isEmpty()) {
            return saveFromTmdb(tmdbID);
        }

        Movie movie = optional.get();

        if(movie.isStale()) {
            return updateFromTmdb(movie.getIdentifier());
        }

        return mapper.toMovieResponse(movie);
    }

    public MovieResponse updateFromTmdb(UUID identifier) {
        Movie movie = movieRepository.getByIdentifier(identifier).orElseThrow(() -> new RuntimeException("Filme não encontrado no banco de dados."));

        MovieResponse updatedData = getMovieByTmdbID(movie.getTmdbID());

        Movie updatedMovie = mapper.toMovie(updatedData);
        updatedMovie.setIdentifier(movie.getIdentifier());
        updatedMovie.setCreatedAt(movie.getCreatedAt());
        updatedMovie.setUpdatedAt(LocalDateTime.now());

        updatedMovie = movieRepository.save(updatedMovie);

        return mapper.toMovieResponse(updatedMovie);
    }

    public MovieResponse saveFromTmdb(Integer tmdbID) {
        MovieResponse movieResponse = getMovieByTmdbID(tmdbID);

        Movie movie = mapper.toMovie(movieResponse);

        String slug = movie.getTitle().replace(" ", "-").toLowerCase();
        Optional<Movie> movieBySlug = movieRepository.getMovieBySlug(slug);

        if (movieBySlug.isPresent()) {
            slug += "-" + movie.getReleaseYear();
        }

        movie.setSlug(slug);
        movie.setCreatedAt(LocalDateTime.now());
        movie.setUpdatedAt(LocalDateTime.now());

        movie = movieRepository.save(movie);

        return mapper.toMovieResponse(movie);
    }

    public MovieResponse getMovieBySlug(String slug) {
        Movie movie = movieRepository.getMovieBySlug(slug).orElseThrow(() -> new RuntimeException("Filme não encontrado pelo slug"));
        return mapper.toMovieResponse(movie);
    }

    public List<CrewMemberResponse> getMovieCrewBySlug(String slug) {
        MovieResponse movie = getMovieBySlug(slug);
        return movie.getCrew().stream()
                .map(mapper::crewToCrewResponse)
                .toList();
    }

    public MovieLikesResponse getMovieLikesBySlug(String slug) throws Exception {
        MovieResponse movie = getMovieBySlug(slug);
        List<MovieLikes> movieLikes = movieLikesRepository.findByIdMovieIdentifier(movie.getIdentifier()).orElseThrow(() -> new RuntimeException("Movie not found by id"));
        String userIdentifier = FindUserIdentifierHelper.getIdentifier();
        UserProfileInfo loggedUser = iAccountsClient.getProfileInfoByIdentifier(userIdentifier);
        return MovieLikesResponse.builder()
                .movieId(movie.getIdentifier())
                .movieTitle(movie.getTitle())
                .movieReleaseYear(movie.getReleaseYear())
                .totalLikes(movieLikes.size())
                .likes(movieLikes.stream().map(
                        movieLike -> {
                            return UserLikeResponse.builder()
                                    .userId(loggedUser.getIdentifier())
                                    .username(loggedUser.getUsername())
                                    .avatarUrl(loggedUser.getAvatarUrl())
                                    //.rating()
                                    //.reviewExcepted()
                                    //.reviewId()
                                    .build();
                        }
                ).toList())
                .build();
    }

    public void likeOrUnlikeMovieBySlug(String slug) throws Exception {
        String userIdentifier = FindUserIdentifierHelper.getIdentifier();
        UserProfileInfo loggedUser = iAccountsClient.getProfileInfoByIdentifier(userIdentifier);
        if (loggedUser.getIdentifier() == null) {
            throw new RuntimeException("User identifier is null");
        }
        MovieResponse movie = getMovieBySlug(slug);

        Optional<MovieLikes> movieLike = movieLikesRepository.findAllByIdMovieIdentifierAndIdUserIdentifier(movie.getIdentifier(), loggedUser.getIdentifier());
        if (movieLike.isPresent()) {
            movieLikesRepository.delete(movieLike.get());
        } else {
            movieLikesRepository.save(MovieLikes.builder()
                            .id(MoviesLikesId.builder()
                                    .movieIdentifier(movie.getIdentifier())
                                    .userIdentifier(loggedUser.getIdentifier())
                                    .build())
                    .build());
        }
    }
}
