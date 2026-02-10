package com.example.myfridge.user.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myfridge.user.domain.dto.UserRequestDTO;
import com.example.myfridge.user.domain.dto.UserResponseDTO;
import com.example.myfridge.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @PutMapping("/info")
    @Operation(summary = "회원정보 수정", description = "비밀번호, 이름, 생년, 선호 요리 태그 등을 수정합니다.")
    public ResponseEntity<?> updateInfo(
            Authentication authentication,
            @RequestBody UserRequestDTO request) {
        String userId = (String) authentication.getPrincipal();
        userService.updateUser(userId, request);
        return ResponseEntity.ok(Map.of("message", "회원정보가 수정되었습니다."));
    }
}
