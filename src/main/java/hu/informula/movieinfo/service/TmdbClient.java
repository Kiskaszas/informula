package hu.informula.movieinfo.service;

import hu.informula.movieinfo.dto.MovieItem;
import hu.informula.movieinfo.dto.tmdb.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TmdbClient {

    private final WebClient webClient;

    @Value("${movie.tmdb.apiKey}")
    private String apiKey;

    public List<MovieItem> search(final String title, final String apiKey) {
        this.apiKey = apiKey;
        return search(title);
    }

    public List<MovieItem> search(final String title) {
        var url = "https://api.themoviedb.org/3/search/movie?api_key=" + apiKey + "&query=" + title + "&include_adult=true";

        var response = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(TmdbSearchResponse.class)
                .block();

        if (response == null || response.getResults() == null) {
            return List.of();
        }

        return response.getResults().stream()
                .map(this::getMovieDetails)
                .toList();
    }

    private MovieItem getMovieDetails(final TmdbSearchItem item) {
        var detailUrl = "https://api.themoviedb.org/3/movie/" + item.getId() + "?api_key=" + apiKey;

        var creditsUrl = "https://api.themoviedb.org/3/movie/" + item.getId() + "/credits?api_key=" + apiKey;

        var detail = webClient.get()
                .uri(detailUrl)
                .retrieve()
                .bodyToMono(TmdbMovieDetail.class)
                .block();

        var credits = webClient.get()
                .uri(creditsUrl)
                .retrieve()
                .bodyToMono(TmdbCredits.class)
                .block();

        var directors = credits != null ? credits.getCrew().stream()
                .filter(c -> c.getJob().equals("Director"))
                .map(TmdbCrew::getName)
                .toList() : null;

        return new MovieItem(
                detail != null ? detail.getTitle() : null, detail != null ? detail.getReleaseDate().substring(0, 4) : null, directors);
    }
}