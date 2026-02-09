package com.example.myfridge.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myfridge.user.domain.dto.SignupRequestDTO;
import com.example.myfridge.user.domain.dto.SignupResponseDTO;
import com.example.myfridge.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

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
    private final UserService userService;

    @GetMapping("/check-id")
    @Operation(summary = "아이디 중복 검사", description = """
            회원가입 시 사용할 아이디의 중복 여부를 검사합니다.

            - 반환값이 false이면 이미 사용 중인 아이디입니다.
            - 반환값이 true이면 사용 가능한 신규 아이디입니다.
            """)
    @ApiResponse(responseCode = "200", description = "중복 검사 결과 반환")
    public ResponseEntity<Boolean> checkUserId(
            @Parameter(description = "중복 여부를 검사할 사용자 아이디", example = "user01") @RequestParam(name = "userId") String userId) {
        System.out.println(">>>>> check userId called (Param: " + userId + ")");
        boolean isAvailable = userService.isUserIdAvailable(userId);
        return ResponseEntity.ok(isAvailable);
    }

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = """
            사용자 정보를 입력받아 회원가입을 진행합니다.

            - 선호 태그는 여러 개 선택할 수 있습니다.
            - 아이디 중복은 서버에서 다시 한 번 검증합니다.""")
    public ResponseEntity<SignupResponseDTO> signup(@RequestBody SignupRequestDTO request) {
        System.out.println(">>>>> signup called (Request: " + request + ")");
        userService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}