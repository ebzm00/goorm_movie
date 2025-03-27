package com.shakkib.netflixclone.controllers;

import com.shakkib.netflixclone.dtoes.CommentDTO;
import com.shakkib.netflixclone.dtoes.CommentResponseDTO;
import com.shakkib.netflixclone.entity.Comment;
import com.shakkib.netflixclone.exceptions.CommentDetailsNotFoundException;
import com.shakkib.netflixclone.services.impl.CommentServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/user/v1/comment")
public class CommentController {

    CommentServiceImpl commentServiceImpl;

    @PostMapping("/write")
    public ResponseEntity<CommentDTO> writeComment(@RequestBody CommentDTO commentDTO){

        Comment myComment = commentServiceImpl.writeComment(commentDTO);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/read")
    public ResponseEntity<CommentResponseDTO> readComment(@PathVariable("id") Long id) throws CommentDetailsNotFoundException {
        Comment comment =  commentServiceImpl.getComment(id);

        CommentResponseDTO response = new CommentResponseDTO(comment.getUser().getEmail(), comment.getCreatedAt(), comment.getComment());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all/{id}")
    public List<CommentResponseDTO> readAllCommentsOfUser(@PathVariable("id") Long id) {
        List<Comment> comments = commentServiceImpl.getAllCommentsOfUser(id);
        List<CommentResponseDTO> mycomments = new ArrayList<>();

        for(Comment comment:comments){

            String email = comment.getUser().getEmail();
            LocalDateTime createdAt = comment.getCreatedAt();
            String content = comment.getComment();

            mycomments.add(new CommentResponseDTO(email, createdAt, content));
        }

        return mycomments;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteComment(@PathVariable("id") Long commentId){
        Boolean flag = commentServiceImpl.deleteComment(commentId);
        return ResponseEntity.ok(flag);
    }

    @PutMapping("/edit")
    public ResponseEntity<CommentDTO> editMyComment(Long commentId, String content) throws CommentDetailsNotFoundException {
        commentServiceImpl.editComment(commentId,content);

        return ResponseEntity.ok().build();
    }

}
