package com.shakkib.netflixclone.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Caption {

    @Id
    private Long id;

    private String captionUrl;

    private String nation;

    @ManyToOne
    @JoinColumn(name = "movie_seq")
    private Movie movie;
}
