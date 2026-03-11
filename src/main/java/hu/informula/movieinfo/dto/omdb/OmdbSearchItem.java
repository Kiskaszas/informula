package hu.informula.movieinfo.dto.omdb;

import lombok.Data;

@Data
public class OmdbSearchItem {

    private String Title;
    private String Year;
    private String imdbID;
    private String Type;
    private String Poster;
}