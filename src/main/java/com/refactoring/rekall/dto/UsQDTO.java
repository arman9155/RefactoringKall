package com.refactoring.rekall.dto;

import com.refactoring.rekall.entity.CategoryEntity;
import com.refactoring.rekall.entity.UsQEntity;
import com.refactoring.rekall.entity.UserEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UsQDTO {

    private Integer usqId; // ▷▶ id _ 자동 count _ 1:1 문의 번호

    // -------- ▷▶ usq 가 외래키로 가져오는 DTO ----------------------------------------------
    private UserDTO userDTO; // ▷▶ 유저Id
    private CategoryDTO categoryDTO; // ▷▶ 카테고리Id
// -------------------------------------------------------------------------------------------

    private String title; // ▷▶ 제목
    private String content; // ▷▶ 내용
    private String comment;  // ▷▶ 답글
    private LocalDateTime date1 = LocalDateTime.now();  // ▷▶ 작성일
    private LocalDateTime date2 = LocalDateTime.now(); // ▷▶ 답글 작성일

    private String image1;  // ▷▶ 첨부사진1
    private String image2;  // ▷▶ 첨부사진2


// -------------- ▷▶ Entity -> DTO ---------------------------------------------------------
    public static UsQDTO toUsQDTO(UsQEntity usQEntity) {
        if(usQEntity == null) return null;
        UsQDTO usQDTO = new UsQDTO();

        usQDTO.setUsqId(usQEntity.getUsqId());
        usQDTO.setUserDTO(UserDTO.toUserDTO(usQEntity.getUserEntity()));
        usQDTO.setCategoryDTO(CategoryDTO.toCategoryDTO(usQEntity.getCategoryEntity()));
        usQDTO.setTitle(usQEntity.getTitle());
        usQDTO.setContent(usQEntity.getContent());
        usQDTO.setComment(usQEntity.getComment());
        usQDTO.setDate1(usQEntity.getDate1());
        usQDTO.setDate2(usQEntity.getDate2());
        usQDTO.setImage1(usQEntity.getImage1());
        usQDTO.setImage2(usQEntity.getImage2());

        return usQDTO;
    }
}
