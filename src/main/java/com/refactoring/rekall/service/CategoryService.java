package com.refactoring.rekall.service;

import com.refactoring.rekall.dto.CategoryDTO;
import com.refactoring.rekall.entity.CategoryEntity;
import com.refactoring.rekall.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;


//  -------------- ★ 카테고리 value 보내기 ★ ---------------------------------------------------------------
    public String findByCategoryId(String categoryId) {
        CategoryEntity categoryEntity = categoryRepository.findByCategoryId(categoryId);
        String value = categoryEntity.getCategoryName();
        return value;
    }

//  -------------- ★ 카테고리리스트 찾기 ★ ---------------------------------------------------------------
    public List<CategoryDTO> findCategory(String value) {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();

        for(CategoryEntity categoryEntity : categoryEntityList) {
            if(categoryEntity != null) {
                if(categoryEntity.getCategoryId().contains(value)){
                    categoryDTOList.add(CategoryDTO.toCategoryDTO(categoryEntity));
                }
            }
        }
        return  categoryDTOList;
    }

}
