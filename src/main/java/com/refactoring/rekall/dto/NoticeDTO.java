package com.refactoring.rekall.dto;

import com.refactoring.rekall.entity.NoticeEntity;
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDTO {
    private Integer noticeId; // ▷▶ id _ 자동 count _ 공지사항번호

// -------- ▷▶  notice 가 외래키로 가져오는 Entity ----------------------------------------------
    private CategoryDTO categoryDTO; // ▷▶ 카테고리Id
// -------------------------------------------------------------------------------------------

    private String title; // ▷▶ 제목
    private String content; // ▷▶ 내용
    private LocalDateTime date = LocalDateTime.now(); // ▷▶ 등록날짜
    private int cnt; // ▷▶ 조회수 count

// -------------- ▷▶ Entity -> DTO ---------------------------------------------------------
    public static NoticeDTO toNoticeDTO(NoticeEntity noticeEntity) {
        if(noticeEntity == null) return null;
        NoticeDTO noticeDTO = new NoticeDTO();

        noticeDTO.setNoticeId(noticeEntity.getNoticeId());
        noticeDTO.setCategoryDTO(CategoryDTO.toCategoryDTO(noticeEntity.getCategoryEntity()));
        noticeDTO.setTitle(noticeEntity.getTitle());
        noticeDTO.setContent(noticeEntity.getContent());
        noticeDTO.setDate(noticeEntity.getDate());
        noticeDTO.setCnt(noticeEntity.getCnt());

        return noticeDTO;
    }
}
