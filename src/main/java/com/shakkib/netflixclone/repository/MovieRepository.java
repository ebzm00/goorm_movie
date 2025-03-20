package com.shakkib.netflixclone.repository;

import com.shakkib.netflixclone.entity.Genre;
import com.shakkib.netflixclone.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {
    //    Optional<List<Movie>> findAllByUserId(Long id);
//    List<Movie> findAllByUserId(Long userId);
    Optional<Movie> findFirstByTitleIgnoreCase(String title);

    List<Movie> findByGenre(Genre genre);

    List<Movie> findByTitleContainingIgnoreCase(String keyword);

}