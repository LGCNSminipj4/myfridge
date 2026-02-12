package com.example.myfridge.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@ToString(exclude = "password")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String userId, password, name;
    private Integer birthYear;
}
