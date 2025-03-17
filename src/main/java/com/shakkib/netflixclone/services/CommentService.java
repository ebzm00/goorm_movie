package com.shakkib.netflixclone.services;

import com.shakkib.netflixclone.dtoes.CommentDTO;
import com.shakkib.netflixclone.entity.Comment;
import com.shakkib.netflixclone.exceptions.CommentDetailsNotFoundException;

public interface CommentService {
    Comment writeComment(CommentDTO commentDTO) throws CommentDetailsNotFoundException;
    Boolean deleteComment(Long commentId);
    Comment getComment(Long commentId) throws CommentDetailsNotFoundException;
    Comment editComment(Long commentId, String content) throws CommentDetailsNotFoundException;
}
