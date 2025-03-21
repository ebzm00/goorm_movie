package com.shakkib.netflixclone.repository;

import com.shakkib.netflixclone.entity.BookMark;
import com.shakkib.netflixclone.entity.Movie;
import com.shakkib.netflixclone.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<BookMark, Long> {

    void deleteByUserAndMovie(User user, Movie movie);

    List<BookMark> findAllByUser(User user);

}
