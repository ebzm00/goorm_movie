package com.shakkib.netflixclone.services.impl;

import com.shakkib.netflixclone.dtoes.AdminDTO;
import com.shakkib.netflixclone.entity.Admin;
import com.shakkib.netflixclone.repository.AdminRepository;
import com.shakkib.netflixclone.services.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    public AdminDTO getAdminDTO(Long id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found with id: " + id));

        // Admin 엔티티를 AdminDTO로 변환하여 반환
        return new AdminDTO(admin.getId(), admin.getEmail(), admin.getName());
    }
}
