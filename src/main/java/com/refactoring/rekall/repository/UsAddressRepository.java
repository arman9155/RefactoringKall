package com.refactoring.rekall.repository;

import com.refactoring.rekall.entity.UsAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsAddressRepository extends JpaRepository<UsAddressEntity, Integer> {
    List<UsAddressEntity> findByUserEntityUserId(String loginId);
}
