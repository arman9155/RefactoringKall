package com.refactoring.rekall.entity;


import com.refactoring.rekall.dto.ReviewCmtDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name = "review_cmt")
public class ReviewCmtEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewCmtId; // ▷▶ id _ 자동 count

// -------- ▷▶   reviewId가 외래키로 가져오는 Entity ----------------------------------------------
    @JoinColumn(name = "reviewId")
    @ManyToOne
    private ReviewEntity reviewEntity;  // ▷▶ 후기번호
    @JoinColumn(name = "userId")
    @ManyToOne
    private UserEntity userEntity; // ▷▶ 유저아이디
// -------------------------------------------------------------------------------------------

    @Column(nullable = false, length = 50)
    private String title; // ▷▶ 답글 제목
    @Column(nullable = false, length = 500)
    private String content; // ▷▶ 답글 내용
    @CreationTimestamp // 생성일시
    private LocalDateTime date = LocalDateTime.now(); // ▷▶ 답글 작성일

// -------------- ▷▶ DTO -> Entity --------------------------------------------------------
    public static ReviewCmtEntity toReviewCmtEntity(ReviewCmtDTO reviewCmtDTO) {
        if(reviewCmtDTO == null) return null;
        ReviewCmtEntity reviewCmtEntity = new ReviewCmtEntity();

        reviewCmtEntity.setReviewCmtId(reviewCmtDTO.getReviewCmtId());
        reviewCmtEntity.setReviewEntity(ReviewEntity.toReviewEntity(reviewCmtDTO.getReviewDTO()));
        reviewCmtEntity.setUserEntity(UserEntity.toUserEntity(reviewCmtDTO.getUserDTO()));
        reviewCmtEntity.setTitle(reviewCmtDTO.getTitle());
        reviewCmtEntity.setContent(reviewCmtDTO.getContent());
        reviewCmtEntity.setDate(reviewCmtDTO.getDate());

        return reviewCmtEntity;
    }
}
