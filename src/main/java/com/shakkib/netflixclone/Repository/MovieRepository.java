package com.shakkib.netflixclone.Repository;

import com.shakkib.netflixclone.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie,String> {
    Optional<List<Movie>> findAllByUserId(String id);
}
