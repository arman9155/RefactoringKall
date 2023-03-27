package com.refactoring.rekall.dto;

import com.refactoring.rekall.entity.CategoryEntity;
import lombok.*;

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private String categoryId; // ▷▶ 카테고리 ID
    private String categoryName;  // ▷▶ 카테고리이름 (값)

// -------------- ▷▶ Entity -> DTO ---------------------------------------------------------
    public static CategoryDTO toCategoryDTO(CategoryEntity categoryEntity) {
        if(categoryEntity == null) return null;
        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setCategoryId(categoryEntity.getCategoryId());
        categoryDTO.setCategoryName(categoryEntity.getCategoryName());

        return categoryDTO;
    }
}
