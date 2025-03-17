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
//        Comment comment = convertCommentDTOToCommentEntity(commentDTO);

        Comment myComment = commentServiceImpl.writeComment(commentDTO);

//        CommentDTO myCommentDTO = convertCommentToCommentDTO(myComment);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/read")
    public ResponseEntity<CommentResponseDTO> readComment(@PathVariable("id") Long id) throws CommentDetailsNotFoundException {
        Comment comment =  commentServiceImpl.getComment(id);

//        CommentDTO response = convertCommentToCommentDTO(comment);

        CommentResponseDTO response = new CommentResponseDTO(comment.getUser().getEmail(), comment.getCreatedAt(), comment.getComment());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all/{id}")
    public List<CommentResponseDTO> readAllCommentsOfUser(@PathVariable("id") Long id) throws CommentDetailsNotFoundException {
        List<Comment> comments = commentServiceImpl.getAllCommentsOfUser(id);
//        List<CommentDTO> mycomments = new ArrayList<>();
        List<CommentResponseDTO> mycomments = new ArrayList<>();

        for(Comment comment:comments){

            String email = comment.getUser().getEmail();
            LocalDateTime createdAt = comment.getCreatedAt();
            String content = comment.getComment();
            mycomments.add(new CommentResponseDTO(email, createdAt, content));

//            mycomments.add(convertCommentToCommentDTO(comment));

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
        Comment comment = commentServiceImpl.editComment(commentId,content);
//        CommentDTO commentDTO = convertCommentToCommentDTO(comment);
        return ResponseEntity.ok().build();
    }

//    private Comment convertCommentDTOToCommentEntity(CommentDTO commentDTO){
//        return new Comment(commentDTO.getId(), commentDTO.getUserId(), commentDTO.getUserEmail(), commentDTO.getCommentAt(),commentDTO.getContent());
//    }
//
//    private CommentDTO convertCommentToCommentDTO(Comment comment){
//        return new CommentDTO(comment.getId(), comment.getUserId(), comment.getMovieId(), comment.getUserEmail(),comment.getCommentAt(), comment.getContent());
//    }

}
