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

    @Cacheable(value = "movies", key = "#api + '_' + #title")
    public MovieResponse getMovies(String api, String title) {

        logRepository.save(new SearchedElement(null, api, title, LocalDateTime.now()));

        List<MovieItem> movies = switch (api.toLowerCase()) {
            case "omdb" -> omdbClient.search(title);
            case "tmdb" -> tmdbClient.search(title);
            default -> throw new IllegalArgumentException("Unknown API: " + api);
        };

        return new MovieResponse(movies);
    }
}