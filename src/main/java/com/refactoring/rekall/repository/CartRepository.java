package com.refactoring.rekall.repository;

import com.refactoring.rekall.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Integer> {

//   @Query("select c from CartEntity c join c.userEntity u where u.userId = (:id)")
//    List<CartEntity> findUserId(@Param(id) String userId);

    List<CartEntity> findAllByUserEntityUserId(String loginId);

    @Query("Select MAX(c.cartId) from CartEntity c")
    Integer findId();
}
