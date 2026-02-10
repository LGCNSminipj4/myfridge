package com.example.myfridge.user.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.myfridge.user.domain.User;

@Mapper
public interface UserMapper {
        User findByUserId(String userId);

        int countByUserId(String userId);

        void insertUser(
                        @Param("userId") String userId,
                        @Param("name") String name,
                        @Param("password") String password,
                        @Param("birthYear") Integer birthYear);

        void insertUserPrefer(
                        @Param("userId") String userId,
                        @Param("tagIds") java.util.List<Integer> tagIds);

        List<Integer> findUserPreferTags(String userId);
}
