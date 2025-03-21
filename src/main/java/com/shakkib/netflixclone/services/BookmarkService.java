package com.shakkib.netflixclone.services;

import com.shakkib.netflixclone.dtoes.BookMarkDTO;
import com.shakkib.netflixclone.entity.BookMark;
import com.shakkib.netflixclone.entity.Movie;
import com.shakkib.netflixclone.entity.User;
import com.shakkib.netflixclone.repository.BookmarkRepository;
import com.shakkib.netflixclone.repository.MovieRepository;
import com.shakkib.netflixclone.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public BookmarkService(BookmarkRepository bookmarkRepository, UserRepository userRepository, MovieRepository movieRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    public boolean addBookmark(String email, Long movieId) {
        User user = userRepository.findUserByEmail(email).orElse(null);
        Movie movie = movieRepository.findById(movieId).orElse(null);

        if (user == null) {return false;}
        if (movie == null) {return false;}

        BookMark bookmark = new BookMark(user,movie);

        bookmarkRepository.save(bookmark);

        return true;
    }

    @Transactional
    public boolean removeBookmark(String email, Long movieId) {
        User user = userRepository.findUserByEmail(email).orElse(null);
        Movie movie = movieRepository.findById(movieId).orElse(null);

        if (user == null) {return false;}
        if (movie == null) {return false;}

        bookmarkRepository.deleteByUserAndMovie(user,movie);

        return true;
    }

    public List<BookMarkDTO.Response> getBookmarks(String email) {
        User user = userRepository.findUserByEmail(email).orElse(null);
        if (user == null) {return null;}

        List<BookMark> bookmarks = bookmarkRepository.findAllByUser(user);

        List<BookMarkDTO.Response> responses = bookmarks.stream().map(BookMarkDTO.Response::new).toList();

        return responses;
    }
}
