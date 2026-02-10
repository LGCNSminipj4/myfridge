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

    @Transactional
    public int autoDiscard(String userId) {
        System.out.println(">>>> ingredient service autoDiscard");
        return ingredientMapper.autoDiscard(userId);
    }

    @Transactional(readOnly = true)
    public IngredientResponseDTO getIngredient(Integer ingredientsId) {
        System.out.println(">>>> ingredient service getIngredient");
        return ingredientMapper.selectIngredient(ingredientsId);
    }

    @Transactional(readOnly = true)
    public List<IngredientResponseDTO> getFridge(String userId) {
        System.out.println(">>>> ingredient service getFridge");
        // 현재는 로그인 서비스가 없어 소비기한 지난 식재료 자동삭제는 냉장고 조회 흐름에서 반영
        ingredientMapper.autoDiscard(userId);
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
        // 쓰레기통 조회시 삭제 상태 + 소비기한 30일 초과 식재료 DB 완전 삭제
        ingredientMapper.autoDelete(userId);
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

    @Transactional
    public int autoDelete(String userId) {
        System.out.println(">>>> ingredient service autoDelete");
        return ingredientMapper.autoDelete(userId);
    }

}
