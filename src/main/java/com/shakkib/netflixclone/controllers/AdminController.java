package com.shakkib.netflixclone.controllers;

import com.shakkib.netflixclone.dtoes.AdminDTO;
import com.shakkib.netflixclone.dtoes.UserDTO;
import com.shakkib.netflixclone.exceptions.UserDetailsNotFoundException;
import com.shakkib.netflixclone.services.AdminService;
import com.shakkib.netflixclone.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final UserService userService;  // UserService 주입

    @GetMapping("/admin/{id}")
    public AdminDTO getAdmin(@PathVariable Long id) {
        return adminService.getAdminDTO(id);
    }

    // 모든 사용자 조회 API 250319 GSHAM
    @GetMapping("/admin/users")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();  // UserService 인스턴스를 통해 메서드 호출
    }

    // 사용자 계정 상태 변경 (정지/해제) 250319 GSHAM
    @PutMapping("/admin/users/{userId}/suspend")
    public ResponseEntity<String> changeUserStatus (
            @PathVariable Long userId,
            @RequestParam boolean deleteFlag) {
        try {
            // UserService의 changeUserStatus 메서드 호출
            userService.changeUserStatus(userId, deleteFlag);
            String status = deleteFlag ? "inactive" : "active";
            return ResponseEntity.ok("User status updated to " + status);
        } catch (UserDetailsNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user status");
        }
    }
}

