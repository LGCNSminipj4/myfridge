package com.example.myfridge.user.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int countByUserId(String userId);
}