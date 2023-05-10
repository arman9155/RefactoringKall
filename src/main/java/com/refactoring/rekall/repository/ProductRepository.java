package com.refactoring.rekall.repository;

import com.refactoring.rekall.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    List<ProductEntity> findAllByCategoryEntityCategoryIdOrderByProductIdDesc(String categoryId);

    ProductEntity findByProductId(Integer productId);

    @Query("select max(p.productId) from ProductEntity p where p.name = (:name)")
    Integer findByNameAndDate(@Param("name") String name);
}
