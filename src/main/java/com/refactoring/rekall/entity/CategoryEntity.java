package com.refactoring.rekall.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.refactoring.rekall.dto.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name = "category")
public class CategoryEntity {
    @Id @Column(length = 20)
    private String categoryId; // ▷▶ 카테고리 ID

// -------- ▷▶ 카테고리 ID 를 사용하는 Entity ----------------------------------------------
    @OneToMany(mappedBy = "categoryEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<NoticeEntity> noticeEntities = new ArrayList<>();
    @OneToMany(mappedBy = "categoryEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ProductEntity> productEntities = new ArrayList<>();
    @OneToMany(mappedBy = "categoryEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<UsQEntity> usQEntities = new ArrayList<>();

// -------------------------------------------------------------------------------------------

    @Column(length = 50, nullable = false)
    private String categoryName;  // ▷▶ 카테고리이름 (값)


// -------------- ▷▶ DTO -> Entity --------------------------------------------------------
    public static CategoryEntity toCategoryEntity(CategoryDTO categoryDTO) {
        if(categoryDTO == null) return null;

        CategoryEntity categoryEntity = new CategoryEntity();

        categoryEntity.setCategoryId(categoryDTO.getCategoryId());
        categoryEntity.setCategoryName(categoryDTO.getCategoryName());

        return categoryEntity;
    }
}
