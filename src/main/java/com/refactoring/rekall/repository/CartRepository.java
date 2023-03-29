package com.refactoring.rekall.repository;

import com.refactoring.rekall.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Integer> {

/*   @Query("select c from CartEntity c join c.userEntity u where u.userId = (:id)")
    List<CartEntity> findUserId(@Param(id) String userId);
    ==> id 에 맞는 cart 리스트 뽑기 -> 로그인 구현 시 사용*/
}
