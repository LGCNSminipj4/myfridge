package com.example.myfridge.ingredient.domain.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class IngredientResponseDTO {
    private Integer ingredientsId;
    private String ingredientsName;
    private Integer amount;
    private LocalDate storageDate;
    private LocalDate expirationDate;
    private LocalDate customDate;
    private String storageCondition;
    private String userId;
    private String status;
}
