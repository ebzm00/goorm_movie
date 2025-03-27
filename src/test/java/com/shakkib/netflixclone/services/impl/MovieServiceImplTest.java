package com.shakkib.netflixclone.services.impl;

import com.shakkib.netflixclone.dtoes.MovieCreateDTO;
import com.shakkib.netflixclone.dtoes.MovieDTO;
import com.shakkib.netflixclone.dtoes.MovieListDTO;
import com.shakkib.netflixclone.entity.Genre;
import com.shakkib.netflixclone.entity.Movie;
import com.shakkib.netflixclone.repository.GenreRepository;
import com.shakkib.netflixclone.repository.MovieRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private MovieServiceImpl movieService;


    @Test
    @DisplayName("active한 모든 영화가 출력된다")
    void getAllMovies_shouldReturnListOfMovieListDTO() {
        // given
        Movie movie1 = new Movie(1L, 100L, "Test Title 1", "Original Title 1", "path1.jpg", false, "Overview", LocalDateTime.now().toLocalDate(), new Genre(), LocalDateTime.now(), LocalDateTime.now());
        Movie movie2 = new Movie(2L, 101L, "Test Title 2", "Original Title 2", "path2.jpg", false, "Overview", LocalDateTime.now().toLocalDate(), new Genre(), LocalDateTime.now(), LocalDateTime.now());
        List<Movie> movieList = List.of(movie1, movie2);

        when(movieRepository.findAllByIsUseTrue()).thenReturn(movieList);

        // when
        List<MovieListDTO> result = movieService.getAllMovies();

        // then
        assertEquals(2, result.size());
        assertEquals("Test Title 1", result.get(0).getTitle());
        assertEquals("Test Title 2", result.get(1).getTitle());
    }


    @Test
    @DisplayName("특정 키워드만 반환한다")
    void searchMoviesByKeyword_shouldReturnMoviesStartingWithKeyword() {
        // given
        Genre genre = new Genre(); // 장르는 지금 상관없으니 빈 객체로

        Movie movie1 = new Movie(1L, 101L, "아이언맨", "Ironman", "/ironman.jpg", false, "...", LocalDate.now(), genre, LocalDateTime.now(), LocalDateTime.now());
        Movie movie2 = new Movie(2L, 102L, "아바타", "Avatar", "/avatar.jpg", false, "...", LocalDate.now(), genre, LocalDateTime.now(), LocalDateTime.now());
        Movie movie3 = new Movie(3L, 103L, "레지스탕스", "Interstellar", "/interstellar.jpg", false, "...", LocalDate.now(), genre, LocalDateTime.now(), LocalDateTime.now());

        List<Movie> movies = List.of(movie1, movie2, movie3);

        // keyword에 "아"가 포함된 영화들만 반환한다고 가정
        when(movieRepository.searchMoviesWithKeyword("아")).thenReturn(List.of(movie1, movie2));

        // when
        List<MovieListDTO> result = movieService.searchMoviesByKeyword("아");

        // then
        assertThat(result).hasSize(2);
        assertThat(result).extracting("title").containsExactly("아이언맨", "아바타");
    }

    @Test
    @DisplayName("10000개 데이터 중 특정 키워드만 반환한다")
    void searchMoviesByKeyword_largeDataset_10000() {
        // given
        List<Movie> movies = IntStream.range(0, 10_000)
                .mapToObj(i -> new Movie(
                        (long) i,
                        100L + i,
                        (i % 100 == 0) ? "아이언맨" + i : "기타영화" + i,
                        "Original Title " + i,
                        "/poster" + i + ".jpg",
                        false,
                        "overview",
                        LocalDate.now(),
                        new Genre(),
                        LocalDateTime.now(),
                        LocalDateTime.now()
                ))
                .toList();

        // 아이언맨으로 시작하는 건 총 100개
        when(movieRepository.searchMoviesWithKeyword("아")).thenReturn(
                movies.stream()
                        .filter(m -> m.getTitle().startsWith("아"))
                        .collect(Collectors.toList())
        );

        // when
        long start = System.currentTimeMillis();
        List<MovieListDTO> result = movieService.searchMoviesByKeyword("아");
        long end = System.currentTimeMillis();

        // then
        assertThat(result).hasSize(100);
        assertThat(result).allMatch(movie -> movie.getTitle().startsWith("아이"));
        System.out.println("Execution time: " + (end - start) + " ms");
    }


    @Test
    @DisplayName("장르 이름으로 영화 리스트를 가져올 수 있다")
    void getMoviesByGenre_shouldReturnListOfMovieListDTO_whenGenreExists() {
        // given
        String genreName = "Action";
        Genre genre = new Genre(1L, genreName);
        Movie movie = new Movie(1L, 101L, "Test Title", "Original", "poster.jpg", false, "overview", LocalDate.now(), genre, LocalDateTime.now(), LocalDateTime.now());
        List<Movie> movies = List.of(movie);

        when(genreRepository.findByGenreIgnoreCase(genreName)).thenReturn(Optional.of(genre));
        when(movieRepository.findByGenreAndIsUseTrue(genre)).thenReturn(movies);

        // when
        List<MovieListDTO> result = movieService.getMoviesByGenre(genreName);

        // then
        assertEquals(1, result.size());
        assertEquals("Test Title", result.get(0).getTitle());
    }


    @Test
    @DisplayName("존재하지 않는 장르일 경우 빈 리스트를 반환한다")
    void getMoviesByGenre_shouldReturnEmptyList_whenGenreNotFound() {
        // given
        String genreName = "Fantasy";

        when(genreRepository.findByGenreIgnoreCase(genreName)).thenReturn(Optional.empty());

        // when
        List<MovieListDTO> result = movieService.getMoviesByGenre(genreName);

        // then
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("10000개의데이터에일때 성능측정 - JMeter 실험중")
    void searchMoviesByKeyword_performanceTest() {
        // given
        String keyword = "아";
        List<Movie> bigList = new ArrayList<>();
        Genre genre = new Genre(1L, "SF");

        for (int i = 0; i < 10000; i++) {
            bigList.add(new Movie(
                    (long) i, (long) (1000 + i),
                    keyword + " 테스트 영화 " + i, "Original Title " + i,
                    "poster.jpg", false,
                    "Overview " + i, LocalDate.now(),
                    genre, LocalDateTime.now(), LocalDateTime.now()
            ));
        }

        when(movieRepository.searchMoviesWithKeyword(keyword)).thenReturn(bigList);

        // when
        long start = System.currentTimeMillis();
        List<MovieListDTO> result = movieService.searchMoviesByKeyword(keyword);
        long end = System.currentTimeMillis();

        // then
        System.out.println("Execution time: " + (end - start) + "ms");

        assertThat(result).hasSize(10000);
    }

    @Test
    @DisplayName("비활성화된 영화는 조회 결과에 포함되지 않는다")
    void getAllMovies_shouldExcludeInactiveMovies() {
        // given
        Movie activeMovie = new Movie(1L, 101L, "Active Movie", "Original", "poster.jpg", false, "desc", LocalDate.now(), new Genre(), LocalDateTime.now(), LocalDateTime.now());
        Movie inactiveMovie = new Movie(2L, 102L, "Inactive Movie", "Original", "poster.jpg", false, "desc", LocalDate.now(), new Genre(), LocalDateTime.now(), LocalDateTime.now());

        // 비활성화 시키기
        inactiveMovie.deactivate();

        List<Movie> movies = List.of(activeMovie); // DB에선 비활성화된 영화는 안 꺼내겠지

        when(movieRepository.findAllByIsUseTrue()).thenReturn(movies);

        // when
        List<MovieListDTO> result = movieService.getAllMovies();

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Active Movie");
    }

    @Test
    @DisplayName("특정 영화 ID로 상세 조회 성공")
    void getMovieById_shouldReturnMovieDTO_whenMovieExistsAndIsActive() {
        // given
        Genre genre = new Genre(1L, "SF");
        Movie movie = new Movie(
                1L, 101L, "Inception", "Inception Original",
                "/inception.jpg", false, "Dream movie",
                LocalDate.of(2010, 7, 16),
                genre, LocalDateTime.now(), LocalDateTime.now()
        );

        when(movieRepository.findByIdAndIsUseTrue(1L)).thenReturn(Optional.of(movie));

        // when
        Optional<MovieDTO> result = movieService.getMovieById(1L);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Inception");
        assertThat(result.get().getGenre()).isEqualTo("SF");
    }

    @Test
    @DisplayName("특정 영화 ID로 조회 시 존재하지 않으면 빈 Optional 반환")
    void getMovieById_shouldReturnEmpty_whenMovieNotFound() {
        // given
        when(movieRepository.findByIdAndIsUseTrue(999L)).thenReturn(Optional.empty());

        // when
        Optional<MovieDTO> result = movieService.getMovieById(999L);

        // then
        assertThat(result).isEmpty();
    }


    // saveMovie() 메소드에 대한 텍스트
    @Test
    @DisplayName("영화 저장")
    public void testSaveMovie() {
        // given : 테스트를 위한 가짜 Movie 객체 생성 및 동작 정의
        Movie movie = new Movie();
        movie.setTitle("Test Movie");

        // movieRepository의 save 메소드 호출 시 가짜 Movie 객체를 반환하도록 설정
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        // when : 테스트 대상 메소드 호출
        Movie savedMovie = movieService.saveMovie(movie);

        // then : 결과 검증 - 저장된 Movie 객체의 제목이 동일한지 확인
        assertThat(savedMovie.getTitle()).isEqualTo("Test Movie");
        //save 메소드 호출 횟수 검증
        verify(movieRepository, times(1)).save(movie);
    }

    // convertMovieDTOToMovieEntity() 메소드에 대한 테스트
    @Test
    @DisplayName("영화컨버터")
    public void testConvertMovieDTOToMovieEntity() {
        // given: 가짜 Genre 객체와 MovieCreateDTO 객체 생성
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setGenre("Action");

        MovieCreateDTO movieCreateDTO = new MovieCreateDTO();
        movieCreateDTO.setGenreId(1L);
        movieCreateDTO.setTitle("Converted Movie");

        // genreRepository의 findById 메소드 호출 시 가짜 Genre 객체를 반환하도록 설정
        when(genreRepository.findById(anyLong())).thenReturn(Optional.of(genre));

        // when : 테스트 대상 메소드 호출
        Movie movie = movieService.convertMovieDTOToMovieEntity(movieCreateDTO);

        //then: 변환된 Movie 객체의 제목과 장르가 기대한 대로 설정되었는지 확인
        assertThat(movie.getTitle()).isEqualTo("Converted Movie");

        // 장르의 ID와 이름이 잘 설정 되었는지 검증
        assertThat(movie.getGenre()).isNotNull(); //Genre가 null이 아닌지 확인
        assertThat(movie.getGenre().getId()).isEqualTo(1L); // Genre ID 확인
        assertThat(movie.getGenre().getGenre()).isEqualTo("Action"); // Genre 이름 확인
        // findById 메소드 호출 횟수 검증
        verify(genreRepository, times(1)).findById(1L);
    }
}
