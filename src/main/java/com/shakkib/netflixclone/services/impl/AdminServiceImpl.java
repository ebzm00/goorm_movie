package com.shakkib.netflixclone.services.impl;

import com.shakkib.netflixclone.dtoes.AdminDTO;
import com.shakkib.netflixclone.dtoes.MovieListDTO;
import com.shakkib.netflixclone.entity.Admin;
import com.shakkib.netflixclone.entity.Movie;
import com.shakkib.netflixclone.repository.AdminRepository;
import com.shakkib.netflixclone.repository.MovieRepository;
import com.shakkib.netflixclone.services.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final MovieRepository movieRepository;


    @Override
    public List<MovieListDTO> getAllMoviesIncludingInactive() {
        return movieRepository.findAll()
                .stream()
                .map(MovieListDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * CHW
     * 영화 isUse를 0(비활성화) 처리함
     */
    @Override
    public void deactivateMovie(Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("해당 영화가 존재하지 않습니다."));
        movie.deactivate();
        movieRepository.save(movie);
    }
    /**
     * CHW
     * 영화 isUse를 1(활성화) 처리함
     */
    @Override
    public void activateMovie(Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("해당 영화가 존재하지 않습니다."));
        movie.activate();
        movieRepository.save(movie);
    }

    public AdminDTO getAdminDTO(Long id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found with id: " + id));

        // Admin 엔티티를 AdminDTO로 변환하여 반환
        return new AdminDTO(admin.getId(), admin.getEmail(), admin.getName());
    }
}
