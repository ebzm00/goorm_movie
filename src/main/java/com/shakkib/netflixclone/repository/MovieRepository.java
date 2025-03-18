package com.shakkib.netflixclone.repository;

import com.shakkib.netflixclone.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
