package com.refactoring.rekall.repository;

import com.refactoring.rekall.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, Integer> {

    List<NoticeEntity> findAllByOrderByNoticeIdDesc();

    @Query("select max(n.noticeId) from NoticeEntity n")
    Integer findNoticeId();
}
