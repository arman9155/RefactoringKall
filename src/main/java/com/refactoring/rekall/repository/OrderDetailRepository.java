package com.refactoring.rekall.repository;

import com.refactoring.rekall.entity.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Integer> {


//    List<OrderDetailEntity> findByOrderEntityUserEntityUserIdOrderByOdetailIdDesc(String loginId);

    @Query("select max(od.odetailId) from OrderDetailEntity od")
    Integer findId();
}
