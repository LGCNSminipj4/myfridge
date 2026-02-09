package com.example.myfridge.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myfridge.auth.domain.dto.LoginRequestDTO;
import com.example.myfridge.auth.domain.dto.SignupRequestDTO;
import com.example.myfridge.auth.service.AuthService;
import com.example.myfridge.common.exception.BadRequestException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "인증(Auth)", description = "회원가입 및 인증 관련 API")
public class AuthController {
    private final AuthService authService;

    @GetMapping("/check-id")
    @Operation(summary = "아이디 중복 검사", description = """
            회원가입 시 사용할 아이디의 중복 여부를 검사합니다.

            - 반환값이 false이면 이미 사용 중인 아이디입니다.
            - 반환값이 true이면 사용 가능한 신규 아이디입니다.
            """)
    @ApiResponse(responseCode = "200", description = "중복 검사 결과 반환")
    public ResponseEntity<Boolean> checkUserId(@RequestParam(name = "userId") String userId) {
        System.out.println(">>>>> check userId called (Param: " + userId + ")");
        boolean isAvailable = authService.isUserIdAvailable(userId);
        return ResponseEntity.ok(isAvailable);
    }

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = """
            사용자 정보를 입력받아 회원가입을 진행합니다.

            - 선호 태그는 여러 개 선택할 수 있습니다.
            - 아이디 중복은 서버에서 다시 한 번 검증합니다.""")
    public ResponseEntity<Map<String, Object>> signup(@RequestBody SignupRequestDTO request) {
        System.out.println(">>>>> signup called (Request: " + request + ")");
        try {
            authService.signup(request);
            return ResponseEntity
                    .ok(Map.of("message", "회원가입 성공"));

        } catch (IllegalStateException e) {
            // 409 Conflict
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of(
                            "code", "CONFLICT",
                            "message", e.getMessage()));

        } catch (IllegalArgumentException e) {
            // 400 Bad Request
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "code", "BAD_REQUEST",
                            "message", e.getMessage()));
        }
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = """
            사용자 정보를 입력받아 로그인을 진행합니다.

            - 아이디와 비밀번호가 일치해야 로그인에 성공합니다.""")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        System.out.println(">>>>> login called (Request: " + request + ")");
        try {
            authService.login(request);
            return ResponseEntity.ok("로그인 성공");
        } catch (BadRequestException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "code", "BAD_REQUEST",
                            "message", e.getMessage()));
        }
    }
}