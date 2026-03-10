package hu.informula.movieinfo.service;

import hu.informula.movieinfo.dto.MovieItem;
import hu.informula.movieinfo.dto.MovieResponse;
import hu.informula.movieinfo.entity.SearchedElement;
import hu.informula.movieinfo.repository.SearchedElementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieServiceTest {

    @Mock
    private OmdbClient omdbClient;

    @Mock
    private TmdbClient tmdbClient;

    @Mock
    private SearchedElementRepository searchedElementRepository;

    @InjectMocks
    private MovieService movieService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMovies_OMDB() {
        List<MovieItem> movies = List.of(
                new MovieItem("Avatar", "2009", List.of("James Cameron"))
        );

        when(omdbClient.search("Avatar")).thenReturn(movies);

        MovieResponse response = movieService.getMovies("omdb", "Avatar");

        assertEquals(1, response.getMovies().size());
        verify(omdbClient).search("Avatar");
        verify(searchedElementRepository).save(any(SearchedElement.class));
    }

    @Test
    void testGetMovies_TMDB() {
        List<MovieItem> movies = List.of(
                new MovieItem("Matrix", "1999", List.of("Wachowski"))
        );

        when(tmdbClient.search("Matrix")).thenReturn(movies);

        MovieResponse response = movieService.getMovies("tmdb", "Matrix");

        assertEquals(1, response.getMovies().size());
        verify(tmdbClient).search("Matrix");
        verify(searchedElementRepository).save(any(SearchedElement.class));
    }

    @Test
    void testInvalidApi() {
        assertThrows(IllegalArgumentException.class, () ->
                movieService.getMovies("invalid", "Avatar")
        );
    }
}