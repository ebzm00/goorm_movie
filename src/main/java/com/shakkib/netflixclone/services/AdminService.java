package com.shakkib.netflixclone.services;

import com.shakkib.netflixclone.dtoes.AdminDTO;
import com.shakkib.netflixclone.dtoes.MovieListDTO;

import java.util.List;

public interface AdminService {
    List<MovieListDTO> getAllMoviesIncludingInactive();
    AdminDTO getAdminDTO(Long id);
    void deactivateMovie(Long movieId);
    void activateMovie(Long movieId);
}
