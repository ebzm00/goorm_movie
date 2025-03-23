package com.shakkib.netflixclone.repository;

import com.shakkib.netflixclone.entity.Genre;
import com.shakkib.netflixclone.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {

    Optional<Movie> findFirstByTitleIgnoreCase(String title);
    /**
     * CHW
     * 활성화된(isUse = true) 모든 영화를 조회
     */
    List<Movie> findAllByIsUseTrue();
    /**
     * CHW
     * 활성화된(isUse = true) 장르 검색 후 영화 조회
     */
    //List<Movie> findByGenre(Genre genre);
    List<Movie> findByGenreAndIsUseTrue(Genre genre);
    /**
     * CHW
     * 활성화된(isUse = true) 장르 검색 후 영화 조회
     * 특정 제목과 정확히 일치하는 활성화된 영화를 조회
     */
    Optional<Movie> findFirstByTitleIgnoreCaseAndIsUseTrue(String title);
    /**
     * CHW
     * 활성화된(isUse = true) 장르 검색 후 영화 조회
     * 수정 사항: 한국어를 잘 모르는지 검색어에 없는 영화도 조회 되어 쿼리 추가함
     */
    @Query("SELECT m FROM Movie m WHERE m.isUse = true AND LOWER(m.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Movie> searchMoviesWithKeyword(@Param("keyword") String keyword);
    /**
     * CHW
     * 특정 ID의 활성화된 영화 조회
     */
    Optional<Movie> findByIdAndIsUseTrue(Long id);

    List<Movie> findByTitleContainingIgnoreCase(String keyword);

}