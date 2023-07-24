package com.refactoring.rekall.repository;

import com.refactoring.rekall.entity.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Integer> {


//    List<OrderDetailEntity> findByOrderEntityUserEntityUserIdOrderByOdetailIdDesc(String loginId);

    @Query("select max(od.odetailId) from OrderDetailEntity od")
    Integer findId();

    List<OrderDetailEntity> findByOrderEntityOrderIdOrderByOdetailIdDesc(Integer orderId);
}
