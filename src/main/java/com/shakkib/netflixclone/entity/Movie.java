package com.shakkib.netflixclone.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "movie")
@Getter
@NoArgsConstructor
//@AllArgsConstructor
//@Setter
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
    @Column(name = "movie_id", unique = true, nullable = false)
    private Long movieId;
    private String title;
    private String originalTitle;
    private String posterPath;
    private boolean adult;
    @Column(length = 1000)
    private String overview;
    private LocalDate releaseDate;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    //추가한 생성자
    public Movie(Long seq, Long movieId, String title, String originalTitle, String posterPath,
                 boolean adult, String overview, LocalDate releaseDate,
                 LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.seq = seq;
        this.movieId = movieId;
        this.title = title;
        this.originalTitle = originalTitle;
        this.posterPath = posterPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void updateOverview(String overview) {
        this.overview = overview;
    }

    public void updateTitle(String title) {
        this.title = title;
    }
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
