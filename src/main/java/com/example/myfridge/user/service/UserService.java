package com.example.myfridge.user.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.myfridge.user.domain.User;
import com.example.myfridge.user.domain.dto.UserResponseDTO;
import com.example.myfridge.user.repository.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;

    public UserResponseDTO getUserById(String userId) {
        User user = userMapper.findByUserId(userId);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다.");
        }
        List<Integer> tagIds = userMapper.findUserPreferTags(userId);

        return UserResponseDTO.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .birthYear(user.getBirthYear())
                .tagIds(tagIds)
                .build();
    }
}
