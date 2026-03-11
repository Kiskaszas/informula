package hu.informula.movieinfo.dto.omdb;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class OmdbMovieDetail {

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Year")
    private String year;

    @JsonProperty("Director")
    private String director;
}