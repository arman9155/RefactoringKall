package com.refactoring.rekall.dto;

import com.refactoring.rekall.entity.ReviewCmtEntity;
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCmtDTO {

    private Integer reviewCmtId; // ▷▶ id _ 자동 count

    // -------- ▷▶   reviewId가 외래키로 가져오는 DTO ----------------------------------------------
    private ReviewDTO reviewDTO;  // ▷▶ 후기번호
    private UserDTO userDTO; // ▷▶ 유저아이디
// -------------------------------------------------------------------------------------------

    private String title; // ▷▶ 답글 제목
    private String content; // ▷▶ 답글 내용
    private LocalDateTime date = LocalDateTime.now(); // ▷▶ 답글 작성일

// -------------- ▷▶ Entity -> DTO ---------------------------------------------------------
    public static ReviewCmtDTO toReviewCmtDTO(ReviewCmtEntity reviewCmtEntity) {
        if(reviewCmtEntity == null) return null;
        ReviewCmtDTO reviewCmtDTO = new ReviewCmtDTO();

        reviewCmtDTO.setReviewCmtId(reviewCmtEntity.getReviewCmtId());
        reviewCmtDTO.setReviewDTO(ReviewDTO.toReviewDTO(reviewCmtEntity.getReviewEntity()));
        reviewCmtDTO.setUserDTO(UserDTO.toUserDTO(reviewCmtEntity.getUserEntity()));
        reviewCmtDTO.setTitle(reviewCmtEntity.getTitle());
        reviewCmtDTO.setContent(reviewCmtEntity.getContent());
        reviewCmtDTO.setDate(reviewCmtEntity.getDate());

        return reviewCmtDTO;
    }
}
