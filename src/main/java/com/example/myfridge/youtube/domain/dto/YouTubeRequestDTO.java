package com.example.myfridge.youtube.domain.dto;

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
public class YouTubeRequestDTO {
    private Integer age;
    private String ingredient;
    private List<Integer> preferredTags;
}
