package com.example.myfridge.youtube.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.myfridge.common.constants.PreferTagMapping;
import com.example.myfridge.youtube.domain.dto.YouTubeRequestDTO;
import com.example.myfridge.youtube.domain.dto.YouTubeResponseDTO;
import com.example.myfridge.youtube.domain.dto.YouTubeSearchResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class YouTubeService {
    private final WebClient webClient = WebClient.create();

    @Value("${youtube.api.key}")
    private String apiKey;

    public List<YouTubeResponseDTO> search(YouTubeRequestDTO request) {

        String preferredTagsStr = request.getPreferredTags().stream()
                .map(PreferTagMapping::getTagName)
                .filter(tagName -> !tagName.isEmpty())
                .collect(Collectors.joining(" "));

        String baseQuery = preferredTagsStr + " " + request.getIngredient() + " 요리 레시피";
        String query = (request.getAge() >= 60) ? baseQuery + " 건강식" : baseQuery;
        System.out.println(">>>>> YouTube Search Query: " + query);

        YouTubeSearchResponseDTO response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("www.googleapis.com")
                        .path("/youtube/v3/search")
                        .queryParam("part", "snippet")
                        .queryParam("q", query)
                        .queryParam("type", "video")
                        .queryParam("maxResults", 10)
                        .queryParam("key", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(YouTubeSearchResponseDTO.class)
                .block();

        List<YouTubeResponseDTO> result = response.getItems().stream()
                .map(item -> new YouTubeResponseDTO(
                        item.getSnippet().getTitle(),
                        item.getSnippet().getThumbnails().getMedium().getUrl(),
                        "https://www.youtube.com/watch?v=" + item.getId().getVideoId()))
                .collect(Collectors.toList());
        return result;
    }
}
