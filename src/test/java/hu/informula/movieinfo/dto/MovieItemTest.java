package hu.informula.movieinfo.dto;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieItemTest {

    @Test
    void testDtoMethods() {
        MovieItem item1 = new MovieItem("Avatar", "2009", List.of("James Cameron"));
        MovieItem item2 = new MovieItem("Avatar", "2009", List.of("James Cameron"));

        assertEquals(item1, item2);
        assertEquals(item1.hashCode(), item2.hashCode());
        assertTrue(item1.toString().contains("Avatar"));
    }
}