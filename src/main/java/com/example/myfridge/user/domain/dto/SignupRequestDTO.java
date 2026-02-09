package com.example.myfridge.user.domain.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDTO {
    private String userId, password, name;
    private Integer birthYear;

    // 선호 태그 ID 목록
    private List<Integer> tagIds;
}