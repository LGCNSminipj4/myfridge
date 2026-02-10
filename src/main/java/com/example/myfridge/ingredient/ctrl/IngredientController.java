package com.example.myfridge.ingredient.ctrl;

import org.springframework.web.bind.annotation.RestController;

import com.example.myfridge.ingredient.domain.dto.IngredientCreateRequestDTO;
import com.example.myfridge.ingredient.domain.dto.IngredientRequestDTO;
import com.example.myfridge.ingredient.domain.dto.IngredientResponseDTO;
import com.example.myfridge.ingredient.service.IngredientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ingredients")
@Tag(name = "Ingredients Api", description = "식재료 CRUD 관련 API")
public class IngredientController {
    private final IngredientService ingredientService;

    // RQ-0006
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "식재료 등록 완료"),
            @ApiResponse(responseCode = "400", description = "필수값 누락/잘못된 입력"),
            @ApiResponse(responseCode = "409", description = "존재하지 않는 userId")
    })
    @Operation(summary = "식재료 등록", description = """
            식재료 정보를 입력하여 냉장고에 재료를 추가합니다.

            - 재료명은 null값이 허용되지 않습니다.
            - 등록 날짜가 null일 경우 현재 날짜로 등록됩니다.
            """)
    @PostMapping("/insert")
    public ResponseEntity<Map<String, String>> createIngredient(Authentication authentication,
            @RequestBody IngredientCreateRequestDTO request) {
        System.out.println(">>>> ingredient ctrl path : /insert");
        System.out.println(">>>> params : " + request);

        // 인증된 사용자 정보에서 userId 추출
        String userId = (String) authentication.getPrincipal();
        IngredientRequestDTO dto = IngredientRequestDTO.builder()
                .userId(userId)
                .ingredientsName(request.getIngredientsName())
                .amount(request.getAmount())
                .storageDate(request.getStorageDate())
                .expirationDate(request.getExpirationDate())
                .customDate(request.getCustomDate())
                .storageCondition(request.getStorageCondition())
                .build();
        try {
            ingredientService.createIngredient(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "식재료 등록 완료"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "존재하지 않는 userId 입니다."));
        }

    }

    // RQ-0007
    @Operation(summary = "식재료 수정", description = "기존 식재료 정보를 수정합니다. (ACTIVE/RESTORE 상태만 수정 가능)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "식재료 수정 완료"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "수정 대상 없음"),
            @ApiResponse(responseCode = "409", description = "존재하지 않는 userId")
    })
    @PutMapping("/update/{ingredientsId}")
    public ResponseEntity<Map<String, String>> updateIngredient(@PathVariable Integer ingredientsId,
            @RequestBody IngredientRequestDTO request) {
        System.out.println(">>>> ingredient ctrl path : /update");
        System.out.println(">>>> ingredientsId : " + ingredientsId);
        System.out.println(">>>> params : " + request);

        try {
            int flag = ingredientService.updateIngredient(ingredientsId, request);

            if (flag == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "수정할 식재료가 없습니다."));
            }

            return ResponseEntity.ok(Map.of("message", "식재료 수정 완료"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "존재하지 않는 userId 입니다."));
        }

    }

    // RQ-0008
    @Operation(summary = "식재료 삭제(쓰레기통)", description = "식재료 상태를 DISCARDED로 변경합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "쓰레기통 이동 완료"),
            @ApiResponse(responseCode = "404", description = "삭제 대상 없음")
    })
    @PutMapping("/trash/{ingredientsId}")
    public ResponseEntity<Map<String, String>> discardIngredient(@PathVariable Integer ingredientsId) {
        int flag = ingredientService.discardIngredient(ingredientsId);

        if (flag == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "삭제할 식재료가 없습니다."));
        }
        return ResponseEntity.ok(Map.of("message", "쓰레기통으로 이동 완료"));
    }

    // RQ-0010
    @Operation(summary = "식재료 단건 조회", description = "ACTIVE/RESTORE 상태 식재료를 단건 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "조회 대상 없음")
    })
    @GetMapping("/detail/{ingredientsId}")
    public ResponseEntity<IngredientResponseDTO> getIngredient(
            @Parameter(description = "식재료 ID", example = "1", required = true) @PathVariable(name = "ingredientsId") Integer ingredientsId) {
        IngredientResponseDTO result = ingredientService.getIngredient(ingredientsId);

        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // RQ-0011
    @Operation(summary = "식재료 전체 조회", description = "유통기한 임박순으로 냉장고 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "조회 결과 없음")
    })
    @GetMapping("/fridge")
    public ResponseEntity<List<IngredientResponseDTO>> getFridge(Authentication authentication) {
        // 인증된 사용자 정보에서 userId 추출
        String userId = (String) authentication.getPrincipal();
        List<IngredientResponseDTO> result = ingredientService.getFridge(userId);

        if (result.size() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // RQ-0012
    @Operation(summary = "삭제된 식재료 단건 조회", description = "DISCARDED 상태 식재료를 단건 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "조회 대상 없음")
    })
    @GetMapping("/trash/detail/{ingredientsId}")
    public ResponseEntity<IngredientResponseDTO> getDiscardedIngredient(
            @Parameter(description = "식재료 ID", example = "1", required = true) @PathVariable(name = "ingredientsId") Integer ingredientsId) {
        IngredientResponseDTO result = ingredientService.getDiscardedIngredient(ingredientsId);

        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // RQ-0013
    @Operation(summary = "삭제된 식재료 전체 조회", description = "쓰레기통 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "조회 결과 없음")
    })
    @GetMapping("/trash/{userId}")
    public ResponseEntity<List<IngredientResponseDTO>> getTrash(@PathVariable String userId) {
        List<IngredientResponseDTO> result = ingredientService.getTrash(userId);

        if (result.size() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // RQ-0014
    @Operation(summary = "삭제된 식재료 복구", description = "DISCARDED 상태를 RESTORE로 변경합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "복구 성공"),
            @ApiResponse(responseCode = "404", description = "복구 대상 없음")
    })
    @PutMapping("/trash/restore/{ingredientsId}")
    public ResponseEntity<Map<String, String>> restoreIngredient(@PathVariable Integer ingredientsId) {
        int flag = ingredientService.restoreIngredient(ingredientsId);

        if (flag == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "복구할 식재료가 없습니다."));
        } else {
            return ResponseEntity.ok(Map.of("message", "복구 완료"));
        }
    }

    // RQ-0015
    @Operation(summary = "삭제된 식재료 완전 삭제", description = "DISCARDED 상태 식재료를 DB에서 완전 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "완전 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "삭제 대상 없음")
    })
    @DeleteMapping("/trash/{ingredientsId}")
    public ResponseEntity<Map<String, String>> deleteIngredient(@PathVariable Integer ingredientsId) {
        int flag = ingredientService.deleteIngredient(ingredientsId);

        if (flag == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "삭제할 식재료가 없습니다."));
        } else {
            return ResponseEntity.ok(Map.of("message", "완전 삭제 완료"));
        }
    }
}
