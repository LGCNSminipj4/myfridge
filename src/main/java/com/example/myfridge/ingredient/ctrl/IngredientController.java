package com.example.myfridge.ingredient.ctrl;

import org.springframework.web.bind.annotation.RestController;

import com.example.myfridge.ingredient.domain.dto.IngredientRequestDTO;
import com.example.myfridge.ingredient.domain.dto.IngredientResponseDTO;
import com.example.myfridge.ingredient.service.IngredientService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ingredients")
public class IngredientController {
    private final IngredientService ingredientService;

    // RQ-0006
    @PostMapping("/insert")
    public ResponseEntity<IngredientResponseDTO> createIngredient(@RequestBody IngredientRequestDTO request) {
        System.out.println(">>>> ingredient ctrl path : /insert");
        System.out.println(">>>> params : " + request);

        int flag = ingredientService.createIngredient(request);

        if (flag != 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    // RQ-0007
    @PutMapping("/update/{ingredientsId}")
    public ResponseEntity<Void> updateIngredient(@PathVariable Integer ingredientsId,
            @RequestBody IngredientRequestDTO request) {
        System.out.println(">>>> ingredient ctrl path : /update");
        System.out.println(">>>> ingredientsId : " + ingredientsId);
        System.out.println(">>>> params : " + request);

        int flag = ingredientService.updateIngredient(ingredientsId, request);

        if (flag != 0) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    // RQ-0008
    @PutMapping("/trash/{ingredientsId}")
    public ResponseEntity<Void> discardIngredient(@PathVariable Integer ingredientsId) {
        int flag = ingredientService.discardIngredient(ingredientsId);

        if (flag != 0) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    // RQ-0009

    // RQ-0010
    @GetMapping("/detail/{ingredientsId}")
    public ResponseEntity<IngredientResponseDTO> getIngredient(@PathVariable Integer ingredientsId) {
        IngredientResponseDTO result = ingredientService.getIngredient(ingredientsId);

        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // RQ-0011
    @GetMapping("/fridge/{userId}")
    public ResponseEntity<List<IngredientResponseDTO>> getFridge(@PathVariable String userId) {
        List<IngredientResponseDTO> result = ingredientService.getFridge(userId);

        if (result.size() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // RQ-0012
    @GetMapping("/trash/detail/{ingredientsId}")
    public ResponseEntity<IngredientResponseDTO> getDiscardedIngredient(@PathVariable Integer ingredientsId) {
        IngredientResponseDTO result = ingredientService.getDiscardedIngredient(ingredientsId);

        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // RQ-0013
    @GetMapping("/trash/{userId}")
    public ResponseEntity<List<IngredientResponseDTO>> getTrash(@PathVariable String userId) {
        List<IngredientResponseDTO> result = ingredientService.getTrash(userId);

        if (result.size() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // RQ-0014
    @PutMapping("/trash/restore/{ingredientsId}")
    public ResponseEntity<Void> restoreIngredient(@PathVariable Integer ingredientsId) {
        int flag = ingredientService.restoreIngredient(ingredientsId);

        if (flag != 0) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // RQ-0015
    @DeleteMapping("/trash/{ingredientsId}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Integer ingredientsId) {
        int flag = ingredientService.deleteIngredient(ingredientsId);

        if (flag != 0) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
