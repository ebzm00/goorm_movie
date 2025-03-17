package com.shakkib.netflixclone.Repository;

import com.shakkib.netflixclone.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,String> {
    Optional<List<Comment>> findAllByUserId(String user_id);
}
