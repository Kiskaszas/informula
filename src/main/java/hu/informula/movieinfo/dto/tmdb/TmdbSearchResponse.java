package hu.informula.movieinfo.dto.tmdb;

import lombok.Data;

import java.util.List;

@Data
public class TmdbSearchResponse {

    private int page;
    private List<TmdbSearchItem> results;
    private int total_pages;
    private int total_results;
}