package hu.informula.movieinfo.dto.tmdb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class TmdbCrew {

    private String job;
    private String name;

}