package hu.informula.movieinfo.service;

import hu.informula.movieinfo.dto.MovieItem;
import hu.informula.movieinfo.dto.MovieResponse;
import hu.informula.movieinfo.entity.SearchedElement;
import hu.informula.movieinfo.repository.SearchedElementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final OmdbClient omdbClient;
    private final TmdbClient tmdbClient;
    private final SearchedElementRepository logRepository;

    @Cacheable(value = "movies", key = "#apiKey + '_' + #title")
    public MovieResponse getMovies(String apiKey, String title) {

        logRepository.save(new SearchedElement(null, apiKey, title, LocalDateTime.now()));

        List<MovieItem> movies;

        if (apiKey.length() == 4) {
            movies = switch (apiKey.toLowerCase()) {
                case "omdb" -> omdbClient.search(title);
                case "tmdb" -> tmdbClient.search(title);
                default -> throw new IllegalStateException("Unexpected value: " + apiKey.toLowerCase());
            };
        } else if (apiKey.length() == 8) {
            movies = omdbClient.search(title, apiKey);
        } else if (apiKey.length() == 32) {
            movies = tmdbClient.search(title, apiKey);
        } else {
            throw new IllegalArgumentException("Invalid API key format: " + apiKey);
        }

        return new MovieResponse(movies);
    }
}