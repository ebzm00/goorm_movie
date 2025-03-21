package com.shakkib.netflixclone.dtoes;

import com.shakkib.netflixclone.entity.BookMark;
import com.shakkib.netflixclone.entity.Movie;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class BookMarkDTO {

    @Getter
    @Setter
    public static class Response {
        private String title;
        private LocalDateTime createdAt;

        public Response(Movie movie) {
            this.title = movie.getTitle();
            this.createdAt = movie.getCreatedAt();
        }
        public Response(BookMark bookMark) {
            this.title = bookMark.getMoive().getTitle();
            this.createdAt = bookMark.getCreatedAt();
        }
    }


}
