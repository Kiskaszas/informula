package hu.informula.movieinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieItem implements Serializable {
    private String title;
    private String year;
    private List<String> directors;
}