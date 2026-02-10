package com.example.myfridge.ingredient.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.myfridge.ingredient.domain.dto.IngredientRequestDTO;
import com.example.myfridge.ingredient.domain.dto.IngredientResponseDTO;

@Mapper
public interface IngredientMapper {
    int insertIngredient(IngredientRequestDTO request);

    int updateIngredient(
            @Param("ingredientsId") Integer ingredientsId,
            @Param("request") IngredientRequestDTO request);

    int discardIngredient(Integer ingredientsId);

    int autoDiscard(String userId);

    IngredientResponseDTO selectIngredient(Integer ingredientsId);

    List<IngredientResponseDTO> selectFridge(String userId);

    IngredientResponseDTO selectDiscardedIngredient(Integer ingredientsId);

    List<IngredientResponseDTO> selectTrash(String userId);

    int restoreIngredient(Integer ingredientsId);

    int deleteIngredient(Integer ingredientsId);

    int autoDelete(String userId);

}
