package com.refactoring.rekall.repository;

import com.refactoring.rekall.entity.ProductImgEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImgRepository extends JpaRepository<ProductImgEntity, Integer> {
    @Query("select u from ProductImgEntity u where u.productEntity.productId = (:productId) order by u.imageName asc")
    List<ProductImgEntity> findimg(@Param("productId") Integer productId);

    @Query("Select MAX(u.productimgId) from ProductImgEntity u")
    Integer findId();

    List<ProductImgEntity> findByProductEntityProductId(Integer productId);
}
