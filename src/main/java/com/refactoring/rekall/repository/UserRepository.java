package com.refactoring.rekall.repository;

import com.refactoring.rekall.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByUserId(String userId);

    @Query("select u from UserEntity u where u.userId=(:userId)")
    UserEntity findByUserName(@Param("userId") String userId);

    @Query("select u from UserEntity u where u.userId=(:userId) and u.password=(:password)")
    UserEntity findByUserIdAndPassword(@Param("userId")String userId, @Param("password")String password);

    List<UserEntity> findByStatus(String status);
}
