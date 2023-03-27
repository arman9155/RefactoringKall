package com.refactoring.rekall.entity;

import com.refactoring.rekall.dto.NoticeDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name = "notice")
public class NoticeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer noticeId; // ▷▶ id _ 자동 count _ 공지사항번호

// -------- ▷▶  notice 가 외래키로 가져오는 Entity ----------------------------------------------
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private CategoryEntity categoryEntity; // ▷▶ 카테고리Id
// -------------------------------------------------------------------------------------------

    @Column(nullable = false, length = 50)
    private String title; // ▷▶ 제목
    @Column(nullable = false, length = 1000)
    private String content; // ▷▶ 내용
    @CreationTimestamp // 생성일시
    private LocalDateTime date = LocalDateTime.now(); // ▷▶ 등록날짜
    @ColumnDefault("0")
    @Column(length = 5)
    private int cnt; // ▷▶ 조회수 count

// ------------ ▷▶ 예비컬럼----------------------------------------------------------------------------
    @Column(length = 50)
    private String tmp_1;
    @Column(length = 50)
    private String tmp_2;

// -------------- ▷▶ DTO -> Entity --------------------------------------------------------
    public static NoticeEntity toNoticeEntity(NoticeDTO noticeDTO) {
        if(noticeDTO == null) return null;
        NoticeEntity noticeEntity = new NoticeEntity();

        noticeEntity.setNoticeId(noticeDTO.getNoticeId());
        noticeEntity.setCategoryEntity(CategoryEntity.toCategoryEntity(noticeDTO.getCategoryDTO()));
        noticeEntity.setTitle(noticeDTO.getTitle());
        noticeEntity.setContent(noticeDTO.getContent());
        noticeEntity.setDate(noticeDTO.getDate());
        noticeEntity.setCnt(noticeDTO.getCnt());

        return noticeEntity;
    }
}
