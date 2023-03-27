package com.refactoring.rekall.repository;

import com.refactoring.rekall.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    List<ProductEntity> findAllByCategoryEntityCategoryIdOrderByProductIdDesc(String categoryId);
}
