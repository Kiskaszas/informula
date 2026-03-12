package hu.informula.movieinfo.controller;

import hu.informula.movieinfo.dto.MovieResponse;
import hu.informula.movieinfo.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/{title}")
    public MovieResponse getMovies(
            @PathVariable final String title,
            @RequestParam final String apiKey
    ) {
        return movieService.getMovies(apiKey, title);
    }
}