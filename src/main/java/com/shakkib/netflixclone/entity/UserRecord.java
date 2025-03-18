package com.shakkib.netflixclone.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class UserRecord {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime watchedAt;

    @ManyToOne
    @JoinColumn(name = "user_seq")
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_seq")
    private Movie movie;

}
