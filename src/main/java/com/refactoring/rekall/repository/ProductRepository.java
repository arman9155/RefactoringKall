package com.refactoring.rekall.repository;

import com.refactoring.rekall.dto.ProductDTO;
import com.refactoring.rekall.entity.ProductEntity;
import groovyjarjarpicocli.CommandLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    List<ProductEntity> findAllByCategoryEntityCategoryIdOrderByProductIdDesc(String categoryId);

    ProductEntity findByProductId(Integer productId);

    @Query("select p from ProductEntity p where p.tag like %:tag%")
    Optional<ProductEntity> findByTag(@Param("tag") String tag);

    @Query("select max(p.productId) from ProductEntity p")
    Integer findId();

    ProductEntity findByName(String name);
}
