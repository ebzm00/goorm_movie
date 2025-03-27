package com.shakkib.netflixclone.services.impl;

import com.shakkib.netflixclone.dtoes.CommentDTO;
import com.shakkib.netflixclone.entity.Comment;
import com.shakkib.netflixclone.entity.Movie;
import com.shakkib.netflixclone.entity.User;
import com.shakkib.netflixclone.exceptions.CommentDetailsNotFoundException;
import com.shakkib.netflixclone.repository.CommentRepository;
import com.shakkib.netflixclone.repository.MovieRepository;
import com.shakkib.netflixclone.repository.UserRepository;
import com.shakkib.netflixclone.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final MovieRepository movieRepository;

    @Override //등록
    public Comment writeComment(CommentDTO commentDTO) {

        User user = userRepository.findById(commentDTO.getUserId()).orElseThrow();
        Movie movie = movieRepository.findById(commentDTO.getMovieId()).orElseThrow();

        Comment comment = commentDTO.toComment(user,movie);
        commentRepository.save(comment);
        return comment;
    }

    @Override //삭제
    public Boolean deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
        return true;
    }

    @Override
    public Comment getComment(Long commentId) throws CommentDetailsNotFoundException {

        return commentRepository.findById(commentId).orElseGet(Comment::new);

    }

    @Override //수정
    public Comment editComment(Long commentId, String content) throws CommentDetailsNotFoundException {
        Comment comment = getComment(commentId);
        Comment updatedcomment = new Comment(comment,content); //생성자로 수정된 값 저장

        return commentRepository.save(updatedcomment);
    }


    public List<Comment> getAllCommentsOfUser(Long user_id) {
        User user = userRepository.findById(user_id).orElseThrow();
        return commentRepository.findByUser(user);
    }
}
