//package com.shakkib.netflixclone.services.impl;
//
//import com.shakkib.netflixclone.Repository.MovieRepositoryBack;
//import com.shakkib.netflixclone.entity.MovieBack;
//import com.shakkib.netflixclone.exceptions.MovieDetailsNotFoundException;
//import com.shakkib.netflixclone.exceptions.UserDetailsNotFoundException;
//import com.shakkib.netflixclone.services.MovieServiceBack;
//import lombok.AllArgsConstructor;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.util.List;
//
//@Service
//@AllArgsConstructor
//public class MovieServiceImplBack implements MovieServiceBack {
//
//    static final Logger LOGGER = LoggerFactory.getLogger(MovieServiceImplBack.class);
//
//    MovieRepositoryBack movieDao;
//
//    @Autowired
//    private UserServiceImpl userServiceImpl;
//
//    @Override
//    public List<MovieBack> fetchMovie(String user_id) throws MovieDetailsNotFoundException {
//        LOGGER.debug("Finding movies of userList : " + user_id);
//        LOGGER.info(" fetchmovie method is triggered"+ user_id);
//        List<MovieBack> list =  movieDao.findAllByUserId(user_id).orElseThrow(()->new MovieDetailsNotFoundException("Movie with id does not exists"));
//        System.out.printf("Returning the saved movies of users %s%n",list.size());
//        return list;
//    }
//
//    @Override
//    public MovieBack addMovie(MovieBack movie) {
//        LOGGER.debug("add movie with details"+ movie);
//        LOGGER.info("add method movie has been triggered");
//        MovieBack movie1 = movieDao.save(movie);
//        return movie1;
//    }
//
//    @Override
//    public boolean deleteMovie(String id) {
//        movieDao.deleteById(id);
//        return true;
//    }
//
//    public Boolean checkUser(String user_id) throws UserDetailsNotFoundException {
//        return userServiceImpl.checkUserByUserId(user_id);
//    }
//}
