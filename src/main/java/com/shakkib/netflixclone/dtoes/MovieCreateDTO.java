package com.shakkib.netflixclone.dtoes;

import com.shakkib.netflixclone.entity.Movie;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MovieCreateDTO {
    private Long movieId;
    private String title;
    private String originalTitle;
    private String posterPath;
    private String overview;
    private LocalDate releaseDate;
    private Long genreId;
    private boolean adult;

    public MovieCreateDTO() {
    }

    public MovieCreateDTO(Movie movie) {
        this.movieId = movie.getMovieId();
        this.title = movie.getTitle();
        this.originalTitle = movie.getOriginalTitle();
        this.posterPath = movie.getPosterPath();
        this.overview = movie.getOverview();
        this.releaseDate = movie.getReleaseDate();
        this.adult = movie.isAdult();
    }
}
