package hu.informula.movieinfo.service;

import hu.informula.movieinfo.dto.MovieItem;
import hu.informula.movieinfo.dto.tmdb.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TmdbClientTest {

    @InjectMocks
    private TmdbClient tmdbClient;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMapTmdbDetailToMovieItem() {
        TmdbMovieDetail detail = new TmdbMovieDetail();
        detail.setTitle("Inception");
        detail.setReleaseDate("2010-07-16");

        TmdbCredits credits = new TmdbCredits();
        credits.setCrew(List.of(
                new TmdbCrew("Director", "Christopher Nolan")
        ));

        MovieItem item = new MovieItem(
                detail.getTitle(),
                detail.getReleaseDate().substring(0, 4),
                List.of("Christopher Nolan")
        );

        assertEquals("Inception", item.getTitle());
        assertEquals("2010", item.getYear());
        assertEquals(1, item.getDirectors().size());
    }
}