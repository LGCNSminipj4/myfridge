package com.example.myfridge.youtube.domain.dto;

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
public class YouTubeResponseDTO {
    private String title;
    private String thumbnailUrl;
    private String videoUrl;
}