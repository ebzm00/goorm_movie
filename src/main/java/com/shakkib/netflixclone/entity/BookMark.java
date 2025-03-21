package com.shakkib.netflixclone.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
public class BookMark {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    public BookMark() {

    }
    public BookMark(User user, Movie movie) {
        this.user = user;
        this.movie = movie;
        this.createdAt = LocalDateTime.now();
    }

    public Movie getMoive(){
        return this.movie;
    }


}
