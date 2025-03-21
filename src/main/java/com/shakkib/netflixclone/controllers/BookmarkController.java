package com.shakkib.netflixclone.controllers;

import com.shakkib.netflixclone.dtoes.BookMarkDTO;
import com.shakkib.netflixclone.dtoes.CustomUserDetails;
import com.shakkib.netflixclone.services.BookmarkService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookmarkController {

    private final BookmarkService bookmarkService;

    BookmarkController(final BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    //북마크 등록
    @GetMapping("/bookmark/{movieId}")
    public ResponseEntity<?> bookmark(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable("movieId") Long movieId) {
        if(customUserDetails == null) {
            System.out.println("CustomUserDetails is null");
        }
        String email = customUserDetails.getEmail();
        System.out.println(email);

        if (bookmarkService.addBookmark(email, movieId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();

    }

    //북마크 삭제
    @DeleteMapping("/bookmark/{movieId}")
    public ResponseEntity<?> deleteBookmark(@AuthenticationPrincipal CustomUserDetails customUserDetails ,@PathVariable("movieId") Long movieId) {
        String email = customUserDetails.getEmail();
        if (bookmarkService.removeBookmark(email, movieId)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    //북마크 조회
    @GetMapping("/bookmarks")
    public List<BookMarkDTO.Response> getAllBookmarks(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String email = customUserDetails.getEmail();

        return bookmarkService.getBookmarks(email);

    }
}
