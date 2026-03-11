package hu.informula.movieinfo.dto.tmdb;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class TmdbSearchItem {

    private Long id;

    private String title;

    @JsonProperty("release_date")
    private String releaseDate;
}