package hu.informula.movieinfo.controller;

import hu.informula.movieinfo.dto.MovieItem;
import hu.informula.movieinfo.dto.MovieResponse;
import hu.informula.movieinfo.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MovieControllerTest {

    @Test
    void testGetMovies() throws Exception {
        MovieService movieService = mock(MovieService.class);

        MovieResponse mockResponse = new MovieResponse(
                List.of(new MovieItem("Avatar", "2009", List.of("James Cameron")))
        );

        when(movieService.getMovies("omdb", "Avatar")).thenReturn(mockResponse);

        MovieController controller = new MovieController(movieService);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        mvc.perform(get("/movies/Avatar?api=omdb")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movies[0].title").value("Avatar"))
                .andExpect(jsonPath("$.movies[0].year").value("2009"));

        verify(movieService, times(1)).getMovies("omdb", "Avatar");
    }
}