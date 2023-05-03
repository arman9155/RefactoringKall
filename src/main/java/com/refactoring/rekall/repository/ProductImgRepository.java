package com.refactoring.rekall.repository;

import com.refactoring.rekall.entity.ProductImgEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImgRepository extends JpaRepository<ProductImgEntity,Integer> {
    List<ProductImgEntity> findByProductEntityProductId(Integer productId);

}
