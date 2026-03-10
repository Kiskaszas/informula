package hu.informula.movieinfo.service;

import hu.informula.movieinfo.dto.MovieItem;
import hu.informula.movieinfo.dto.omdb.OmdbMovieDetail;
import hu.informula.movieinfo.dto.omdb.OmdbSearchItem;
import hu.informula.movieinfo.dto.omdb.OmdbSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OmdbClient {

    private final WebClient webClient = WebClient.create();

    @Value("${movie.omdb.apiKey}")
    private String apiKey;

    public List<MovieItem> search(final String title) {
        var url = "https://www.omdbapi.com/?s=" + title + "&apikey=" + apiKey;

        var response = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(OmdbSearchResponse.class)
                .block();

        if (response == null || response.getSearch() == null) {
            return List.of();
        }

        return response.getSearch().stream()
                .map(this::getMovieDetails)
                .toList();
    }

    private MovieItem getMovieDetails(final OmdbSearchItem item) {
        var url = "https://www.omdbapi.com/?i=" + item.getImdbID() + "&apikey=" + apiKey;

        var detail = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(OmdbMovieDetail.class)
                .block();

        return new MovieItem(
                detail != null ? detail.getTitle() : null,
                detail != null ? detail.getYear() : null,
                List.of(detail != null ? detail.getDirector().split(", ") : null)
        );
    }
}