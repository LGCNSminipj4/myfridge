package com.example.myfridge.youtube.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.myfridge.user.domain.dto.UserResponseDTO;
import com.example.myfridge.user.service.UserService;
import com.example.myfridge.youtube.domain.dto.YouTubeRequestDTO;
import com.example.myfridge.youtube.domain.dto.YouTubeResponseDTO;
import com.example.myfridge.youtube.service.YouTubeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/youtube")
@RequiredArgsConstructor
@Tag(name = "유튜브(Youtube)", description = "유튜브 영상 검색 관련 API")
public class YouTubeController {
    private final YouTubeService youtubeService;
    private final UserService userService;

    @PostMapping("/recipes/search")
    @Operation(summary = "유튜브 영상 검색", description = """
            특정 재료에 대한 유튜브 영상들을 검색합니다.

            - 쿼리 파라미터에 재료명을 입력하여 해당 재료에 대한 요리 영상을 검색합니다.
            - request body에 선호 요리 태그가 있는 경우 해당 태그를 기반으로 검색합니다. (Integer 배열)
            - request body에 빈 배열이 있는 경우, 사용자 정보에 저장된 선호 요리 태그를 기반으로 검색합니다.
            """)
    public ResponseEntity<List<YouTubeResponseDTO>> search(Authentication authentication,
            @RequestParam(name = "ingredientName") String ingredientName,
            @RequestBody List<Integer> tag_ids) {
        String userId = (String) authentication.getPrincipal();
        UserResponseDTO userInfo = userService.getUserById(userId);

        Integer age = userInfo.getBirthYear() != null ? (2026 - userInfo.getBirthYear() + 1) : 0;

        YouTubeRequestDTO request = YouTubeRequestDTO.builder()
                .age(age)
                .ingredient(ingredientName)
                .preferredTags(tag_ids.size() > 0 ? tag_ids : userInfo.getTagIds())
                .build();

        return ResponseEntity.ok(youtubeService.search(request));
    }

}
