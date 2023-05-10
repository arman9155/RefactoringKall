package com.refactoring.rekall.repository;

import com.refactoring.rekall.entity.UsQEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsQRepository extends JpaRepository<UsQEntity, Integer> {
   List<UsQEntity> findByCategoryEntityCategoryIdOrderByUsqIdDesc(String categoryId);

    List<UsQEntity> findByUserEntityUserIdOrderByUsqIdDesc(String loginId);

}
