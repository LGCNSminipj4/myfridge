package com.example.myfridge.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myfridge.user.domain.User;
import com.example.myfridge.user.domain.dto.UserResponseDTO;
import com.example.myfridge.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "사용자(User)", description = "사용자 정보 관리 API")
public class UserController {
    private final UserService userService;

    @GetMapping("/info")
    @Operation(summary = "사용자 정보 조회", description = """
            로그인된 사용자의 정보를 조회합니다.
            """)
    public ResponseEntity<UserResponseDTO> Info(Authentication authentication) {
        // 인증된 사용자 정보에서 userId 추출
        String userId = (String) authentication.getPrincipal();
        UserResponseDTO response = userService.getUserById(userId);
        return ResponseEntity.ok(response);
    }
}
