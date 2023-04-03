package com.refactoring.rekall.repository;

import com.refactoring.rekall.dto.WishListDTO;
import com.refactoring.rekall.entity.WishListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishListEntity, Integer> {
    List<WishListEntity> findByUserEntityUserId(String userId);
}
