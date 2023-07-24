package com.refactoring.rekall.repository;

import com.refactoring.rekall.dto.OrderDTO;
import com.refactoring.rekall.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Repository

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    List<OrderEntity> findByUserEntityUserIdOrderByOrderIdDesc(String loginId);

    @Query("Select o from OrderEntity o where orderId = (Select Max(o2.id) from OrderEntity o2 where o2.userEntity.userId = (:loginId))")
    Optional<OrderEntity> findRecentOrderDTO(@Param("loginId") String loginId);

//    Optional<OrderEntity> findByOrderDetailEntityOdetailId(Integer odetailId); 이거 없애야함
}
