package com.sesac.aibackend.controller;

import com.sesac.aibackend.repository.UserRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 관리자 전용 사용자 관리 라우트 (Day 5 역할 권한 시연).
 *
 * SecurityConfig의 requestMatchers("/admin/**").hasRole("ADMIN")와
 * 메서드 단의 @PreAuthorize("hasRole('ADMIN')") 양쪽으로 이중 보호합니다.
 *
 * 시드 계정: admin / admin1234 (DataInitializer, dev 프로파일 한정)
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

    private final UserRepository userRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public List<Map<String, Object>> listUsers() {
        return userRepository.findAll().stream()
                .map(user -> Map.<String, Object>of(
                        "id", user.getId(),
                        "username", user.getUsername(),
                        "role", user.getRole().name(),
                        "provider", user.getProvider()
                ))
                .toList();
    }
}
