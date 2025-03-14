package com.shakkib.netflixclone.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
    private String title;
    private String outLine;
    private String imageUrl;
    private String plot;
    private String casting;
    private String director;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime openAt;
    private LocalDateTime releasedAt;
    private int Active;

    @ManyToOne
    @JoinColumn(name = "genre_seq")
    private Genre genre;
}
