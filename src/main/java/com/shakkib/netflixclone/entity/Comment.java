package com.shakkib.netflixclone.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Seq;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_seq")
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_seq")
    private Movie movie;

    public Comment(String conmment, LocalDateTime createdAt, User user, Movie movie) {
        this.comment = conmment;
        this.createdAt = createdAt;
        this.user = user;
        this.movie = movie;
    }

    public Comment(Comment comment, String content) {
        this.Seq = comment.Seq;
        this.comment = content;
        this.createdAt = comment.createdAt;
        this.updatedAt = LocalDateTime.now();
        this.comment = comment.comment;
    }

    public Comment() {}

    public String getComment() {
        return comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }
}
