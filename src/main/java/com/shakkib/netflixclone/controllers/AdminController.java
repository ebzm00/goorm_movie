package com.shakkib.netflixclone.controllers;

import com.shakkib.netflixclone.dtoes.AdminDTO;
import com.shakkib.netflixclone.dtoes.MovieCreateDTO;
import com.shakkib.netflixclone.dtoes.UserDTO;
import com.shakkib.netflixclone.entity.Genre;
import com.shakkib.netflixclone.entity.Movie;
import com.shakkib.netflixclone.exceptions.UserDetailsNotFoundException;
import com.shakkib.netflixclone.repository.GenreRepository;
import com.shakkib.netflixclone.services.AdminService;
import com.shakkib.netflixclone.services.MovieService;
import com.shakkib.netflixclone.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final UserService userService;  // UserService 주입
    private final MovieService movieService; // movieService 주입
    private final GenreRepository genreRepository;

    @GetMapping("/admin/{id}")
    public AdminDTO getAdmin(@PathVariable Long id) {
        return adminService.getAdminDTO(id);
    }

    // 모든 사용자 조회 API 250319 GSHAM
    // isDeleteFlag 활성/비활성 여부에 따라 사용자 조회 추가 250321 GSHAM
    @GetMapping("/admin/users")
    public List<UserDTO> getAllUsers(@RequestParam(value = "isActive", required = false) Boolean isActive) {
        return userService.getAllUsers(isActive);  // UserService 인스턴스를 통해 메서드 호출
    }

    // 사용자 계정 상태 변경 (정지/해제) 250319 GSHAM
    @PutMapping("/admin/users/{userId}/suspend")
    public ResponseEntity<String> changeUserStatus(
            @PathVariable("userId") Long userId,  // ✅ 명확하게 지정
            @RequestParam("deleteFlag") boolean deleteFlag) {
        try {
            // UserService의 changeUserStatus 메서드 호출
            userService.changeUserStatus(userId, deleteFlag);
            String status = deleteFlag ? "비활성화되었습니다." : "활성화되었습니다.";
            return ResponseEntity.ok("사용자 상태가"+ status);
        } catch (UserDetailsNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user status");
        }
    }

    // 250320 GSHAM 영화 저장 메서드 엔드포인트
    @PostMapping("/admin/reg")
    public ResponseEntity<String> addMovie(@RequestBody MovieCreateDTO movieCreateDTO) {
        try {
            // MovieCreateDTO를 Movie 엔티티로 변환
            Movie movie = movieService.convertMovieDTOToMovieEntity(movieCreateDTO);

            // 영화 저장
            movieService.saveMovie(movie);

            // 성공적인 저장 후 응답
            return ResponseEntity.ok("영화가 성공적으로 등록되었습니다.");
        } catch (IllegalArgumentException e) {
            // 예외 발생 시, 유효하지 않은 입력에 대해 처리
            return ResponseEntity.badRequest().body("유효하지 않은 입력 값입니다: " + e.getMessage());
        } catch (Exception e) {
            // 다른 예외에 대해 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("영화 등록에 실패하였습니다. 잠시 후 다시 시도해주세요.");
        }
    }
}
 
