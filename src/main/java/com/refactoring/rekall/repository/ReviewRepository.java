package com.refactoring.rekall.repository;

import com.refactoring.rekall.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {

    @Query("select r from ReviewEntity r join r.productEntity p where p.star>4 order by p.star desc")
    List<ReviewEntity> findProduct();

    List<ReviewEntity> findByProductEntityProductIdOrderByReviewIdDesc(Integer productId);

    List<ReviewEntity> findByUserEntityUserIdOrderByReviewIdDesc(String loginId);
}
