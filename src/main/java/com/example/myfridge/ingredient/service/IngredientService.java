package com.example.myfridge.ingredient.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.myfridge.ingredient.dao.IngredientMapper;
import com.example.myfridge.ingredient.domain.dto.IngredientRequestDTO;
import com.example.myfridge.ingredient.domain.dto.IngredientResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class IngredientService {
    private final IngredientMapper ingredientMapper;

    @Transactional
    public int createIngredient(IngredientRequestDTO request) {
        System.out.println(">>>> ingredient service create");
        return ingredientMapper.insertIngredient(request);

    }

    @Transactional
    public int updateIngredient(Integer ingredientsId, IngredientRequestDTO request) {
        System.out.println(">>>> ingredient service update");
        return ingredientMapper.updateIngredient(ingredientsId, request);
    }

    @Transactional
    public int discardIngredient(Integer ingredientsId) {
        System.out.println(">>>> ingredient service discard");
        return ingredientMapper.discardIngredient(ingredientsId);
    }

    @Transactional(readOnly = true)
    public IngredientResponseDTO getIngredient(Integer ingredientsId) {
        System.out.println(">>>> ingredient service getIngredient");
        return ingredientMapper.selectIngredient(ingredientsId);
    }

    @Transactional(readOnly = true)
    public List<IngredientResponseDTO> getFridge(String userId) {
        System.out.println(">>>> ingredient service getFridge");
        return ingredientMapper.selectFridge(userId);
    }

    @Transactional(readOnly = true)
    public IngredientResponseDTO getDiscardedIngredient(Integer ingredientsId) {
        System.out.println(">>>> ingredient service getDiscardedIngredient");
        return ingredientMapper.selectDiscardedIngredient(ingredientsId);
    }

    @Transactional(readOnly = true)
    public List<IngredientResponseDTO> getTrash(String userId) {
        System.out.println(">>>> ingredient service getTrash");
        return ingredientMapper.selectTrash(userId);
    }

    @Transactional
    public int restoreIngredient(Integer ingredientsId) {
        System.out.println(">>>> ingredient service restore");
        return ingredientMapper.restoreIngredient(ingredientsId);
    }

    @Transactional
    public int deleteIngredient(Integer ingredientsId) {
        System.out.println(">>>> ingredient service deleteIngredient");
        return ingredientMapper.deleteIngredient(ingredientsId);
    }

}
