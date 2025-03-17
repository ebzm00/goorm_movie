package com.shakkib.netflixclone.dtoes;

import com.shakkib.netflixclone.entity.Comment;
import com.shakkib.netflixclone.entity.Movie;
import com.shakkib.netflixclone.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class CommentDTO {
    private Long userId;
    private Long movieId;
    private String userEmail;
    private LocalDateTime commentAt;
    private String content;


    public CommentDTO(Long userId, Long movieId, String userEmail, LocalDateTime commentAt, String content) {
        this.userId = userId;
        this.movieId = movieId;
        this.userEmail = userEmail;
        this.commentAt = LocalDateTime.now();
        this.content = content;
    }

    public Comment toComment(User user, Movie movie) {
        return new Comment(content,commentAt,user,movie);
    }

    public CommentDTO(Comment comment){

    }
}