package hu.informula.movieinfo.dto.tmdb;

import lombok.Data;

import java.util.List;

@Data
public class TmdbCredits {

    private Long id;
    private List<TmdbCrew> crew;
}