package com.refactoring.rekall.repository;

import com.refactoring.rekall.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Integer> {

//   @Query("select c from CartEntity c join c.userEntity u where u.userId = (:id)")
//    List<CartEntity> findUserId(@Param(id) String userId);

    List<CartEntity> findAllByUserEntityUserIdOrderByCartIdDesc(String loginId);

    @Query("Select MAX(c.cartId) from CartEntity c")
    Integer findId();

    @Query("Select c from CartEntity c where c.cartId = (SELECT MAX(c2.cartId) FROM CartEntity c2 WHERE c2.userEntity.userId = (:loginId))")
    CartEntity findUserEntityUserId(@Param("loginId")String loginId);

}
