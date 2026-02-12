package com.example.myfridge.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.myfridge.auth.domain.dto.LoginRequestDTO;
import com.example.myfridge.auth.domain.dto.SignupRequestDTO;
import com.example.myfridge.common.exception.BadRequestException;
import com.example.myfridge.common.util.JwtProvider;
import com.example.myfridge.user.domain.User;
import com.example.myfridge.user.repository.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public boolean isUserIdAvailable(String userId) {
        int count = userMapper.countByUserId(userId);
        return count == 0;
    }

    @Transactional
    public void signup(SignupRequestDTO request) {
        validateSignupRequest(request);

        // 1. 아이디 중복 재검사
        if (userMapper.countByUserId(request.getUserId()) > 0) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }

        // 2. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 3. users 저장
        userMapper.insertUser(
                request.getUserId(),
                request.getName(),
                encodedPassword,
                request.getBirthYear());

        // 4. users_prefer 저장
        if (request.getTagIds() != null && !request.getTagIds().isEmpty()) {
            userMapper.insertUserPrefer(
                    request.getUserId(),
                    request.getTagIds());
        }
    }

    public String login(LoginRequestDTO request) {

        // 1. 아이디 존재 확인
        User user = userMapper.findByUserId(request.getUserId());
        if (user == null) {
            throw new BadRequestException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        // 2. 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        // 3. 로그인 성공
        return jwtProvider.createAccessToken(request.getUserId());
    }

    private void validateSignupRequest(SignupRequestDTO request) {
        if (request.getUserId() == null || request.getUserId().isBlank()
                || request.getName() == null || request.getName().isBlank()
                || request.getPassword() == null || request.getPassword().isBlank()
                || request.getBirthYear() == null) {
            throw new IllegalArgumentException("필수 입력 값이 누락되었습니다.");
        }
    }
}
