package com.refactoring.rekall.repository;

import com.refactoring.rekall.entity.RefundEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefundRepository extends JpaRepository<RefundEntity, Integer> {

    List<RefundEntity> findByUserEntityUserIdOrderByRefundIdDesc(String loginId);

    @Query("select max(r.refundId) from RefundEntity r")
    Integer findId();

    Optional<RefundEntity> findByOrderDetailEntityOdetailId(Integer odetailId);
}
