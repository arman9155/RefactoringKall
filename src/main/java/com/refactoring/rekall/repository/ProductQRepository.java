package com.refactoring.rekall.repository;

import com.refactoring.rekall.entity.ProductQEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductQRepository extends JpaRepository<ProductQEntity, Integer> {
    List<ProductQEntity> findByProductEntityProductId(Integer productId);
}
