package com.refactoring.rekall.repository;

import com.refactoring.rekall.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

import static javax.persistence.LockModeType.PESSIMISTIC_READ;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints({@QueryHint(name="javax.persistence.lock.scope" , value="EXTENDED"),
                 @QueryHint(name="javax.persistence.lock.timeout", value="120000")})
    Optional<UserEntity> findByUserId(String userId);

    @Query("select u from UserEntity u where u.userId=(:userId)")
    UserEntity findByUserName(@Param("userId") String userId);

    @Query("select u from UserEntity u where u.userId=(:userId) and u.password=(:password)")
    UserEntity findByUserIdAndPassword(@Param("userId")String userId, @Param("password")String password);

    List<UserEntity> findByStatus(String status);

    @Query("select u from UserEntity u where u.role = (:id)")
    List<UserEntity> findAllByRole(@Param("id")String id);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints({@QueryHint(name="javax.persistence.lock.scope" , value="EXTENDED"),
                @QueryHint(name="javax.persistence.lock.timeout", value="120000")})
    UserEntity save(UserEntity userEntity);

}

//왜 갑ㅈ기 이거 설정하니까 send 에러에요? --> favcon  뭐시기가 문제
