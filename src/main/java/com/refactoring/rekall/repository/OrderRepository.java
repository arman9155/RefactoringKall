package com.refactoring.rekall.repository;

import com.refactoring.rekall.dto.OrderDTO;
import com.refactoring.rekall.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
    List<OrderEntity> findByUserEntityUserIdOrderByOrderIdDesc(String loginId);

    Optional<OrderEntity> findByOrderDetailEntityOdetailId(Integer odetailId);
}
