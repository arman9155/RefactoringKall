package com.refactoring.rekall.repository;

import com.refactoring.rekall.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    List<ProductEntity> findAllByCategoryEntityCategoryIdOrderByProductIdDesc(String categoryId);

    ProductEntity findByProductId(Integer productId);
}
