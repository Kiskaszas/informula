package hu.informula.movieinfo.service;

import hu.informula.movieinfo.dto.MovieItem;
import hu.informula.movieinfo.dto.omdb.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OmdbClientTest {

    @InjectMocks
    private OmdbClient omdbClient;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMapOmdbDetailToMovieItem() {
        OmdbMovieDetail detail = new OmdbMovieDetail();
        detail.setTitle("Avatar");
        detail.setYear("2009");
        detail.setDirector("James Cameron");

        MovieItem item = new MovieItem(
                detail.getTitle(),
                detail.getYear(),
                List.of(detail.getDirector().split(", "))
        );

        assertEquals("Avatar", item.getTitle());
        assertEquals("2009", item.getYear());
        assertEquals(1, item.getDirectors().size());
    }
}