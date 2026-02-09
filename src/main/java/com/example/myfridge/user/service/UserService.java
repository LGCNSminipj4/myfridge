package com.example.myfridge.user.service;

import org.springframework.stereotype.Service;

import com.example.myfridge.user.repository.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;

    public boolean isUserIdAvailable(String userId) {
        int count = userMapper.countByUserId(userId);
        return count == 0;
    }
}