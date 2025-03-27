package com.shakkib.netflixclone.controllers;

import com.shakkib.netflixclone.dtoes.BookMarkDTO;
import com.shakkib.netflixclone.dtoes.CustomUserDetails;
import com.shakkib.netflixclone.services.impl.BookmarkService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookmark")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    BookmarkController(final BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    //북마크 등록
    @GetMapping("/{movieId}")
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
    @DeleteMapping("/{movieId}")
    public ResponseEntity<?> deleteBookmark(@AuthenticationPrincipal CustomUserDetails customUserDetails ,@PathVariable("movieId") Long movieId) {
        String email = customUserDetails.getEmail();
        if (bookmarkService.deleteBookmark(email, movieId)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    //북마크 조회
    @GetMapping("/list")
    public List<BookMarkDTO.Response> getAllBookmarks(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String email = customUserDetails.getEmail();

        List<BookMarkDTO.Response> bookmarks = bookmarkService.getBookmarks(email);

        return bookmarks;
    }
}
