package com.refactoring.rekall.repository;

import com.refactoring.rekall.entity.RefundEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefundRepository extends JpaRepository<RefundEntity, Integer> {

    List<RefundEntity> findByUserEntityUserIdOrderByRefundId(String loginId);
}
