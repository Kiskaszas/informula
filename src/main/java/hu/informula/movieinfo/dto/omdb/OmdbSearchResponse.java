package hu.informula.movieinfo.dto.omdb;

import lombok.Data;

import java.util.List;

@Data
public class OmdbSearchResponse {

    private List<OmdbSearchItem> Search;
    private String totalResults;
    private String Response;
    private String Error;
}
