package com.refactoring.rekall.repository;

import com.refactoring.rekall.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository  extends JpaRepository<CategoryEntity, String> {

    CategoryEntity findByCategoryId(String CategoryId);
}
