package com.refactoring.rekall.repository;

import com.refactoring.rekall.entity.UsQEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsQRepository extends JpaRepository<UsQEntity, Integer> {
    List<UsQEntity> findByUserEntityUserIdOrderByUsqIdDesc(String loginId);
}
