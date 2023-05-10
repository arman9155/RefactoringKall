package com.refactoring.rekall.repository;

import com.refactoring.rekall.dto.WishListDTO;
import com.refactoring.rekall.entity.WishListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishListEntity, Integer> {
    List<WishListEntity> findByUserEntityUserIdOrderByDateDesc(String userId);


    @Query("Select w.wishlistId from WishListEntity w join w.productEntity p join w.userEntity u where p.productId =(:productId) and u.userId = (:userId)")
    Integer findWishListId(@Param("productId") Integer productId, @Param("userId") String userId);
}
