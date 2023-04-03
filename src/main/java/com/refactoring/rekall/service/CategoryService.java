package com.refactoring.rekall.service;

import com.refactoring.rekall.entity.CategoryEntity;
import com.refactoring.rekall.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
}
