package com.refactoring.rekall.entity;


import com.refactoring.rekall.dto.UsQDTO;
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
@Table(name = "us_q")
public class UsQEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer usqId; // ▷▶ id _ 자동 count _ 1:1 문의 번호

// -------- ▷▶ usq 가 외래키로 가져오는 Entity ----------------------------------------------
    @JoinColumn(name = "userId")
    @ManyToOne
    private UserEntity userEntity; // ▷▶ 유저Id
    @JoinColumn(name = "categoryId")
    @ManyToOne
    private CategoryEntity categoryEntity; // ▷▶ 카테고리Id
// -------------------------------------------------------------------------------------------

    @Column(length = 50, nullable = false)
    private String title; // ▷▶ 제목
    @Column(length = 500, nullable = false)
    private String content; // ▷▶ 내용
    @Column(length = 500)
    private String comment;  // ▷▶ 답글
    @CreationTimestamp // 생성일시
    private LocalDateTime date1 = LocalDateTime.now();  // ▷▶ 작성일
    @CreationTimestamp // 답글 - 생성-> 변경
    private LocalDateTime date2 = LocalDateTime.now(); // ▷▶ 답글 작성일
    @Column(length=100)
    private String image1;  // ▷▶ 첨부사진1
    @Column(length=100)
    private String image2;  // ▷▶ 첨부사진2


    // -------------- ▷▶ DTO -> Entity --------------------------------------------------------
    public static UsQEntity toUsQEntity(UsQDTO usQDTO) {
        if(usQDTO == null) return null;
        UsQEntity usQEntity = new UsQEntity();

        usQEntity.setUsqId(usQDTO.getUsqId());
        usQEntity.setUserEntity(UserEntity.toUserEntity(usQDTO.getUserDTO()));
        usQEntity.setCategoryEntity(CategoryEntity.toCategoryEntity(usQDTO.getCategoryDTO()));
        usQEntity.setTitle(usQDTO.getTitle());
        usQEntity.setContent(usQDTO.getContent());
        usQEntity.setComment(usQDTO.getComment());
        usQEntity.setDate1(usQDTO.getDate1());
        usQEntity.setDate2(LocalDateTime.now());
        usQEntity.setImage1(usQDTO.getImage1());
        usQEntity.setImage2(usQDTO.getImage2());

        return usQEntity;
    }

// -------------- ▷▶ DTO -> Entity _ 답글 삭제 ->  null 처리 -------------------------------------
    public static UsQEntity toCommDelEntity(UsQDTO usQDTO) {
        if(usQDTO == null) return null;
        UsQEntity usQEntity = new UsQEntity();

        usQEntity.setUsqId(usQDTO.getUsqId());
        usQEntity.setUserEntity(UserEntity.toUserEntity(usQDTO.getUserDTO()));
        usQEntity.setCategoryEntity(CategoryEntity.toCategoryEntity(usQDTO.getCategoryDTO()));
        usQEntity.setTitle(usQDTO.getTitle());
        usQEntity.setContent(usQDTO.getContent());
        usQEntity.setComment(null);
        usQEntity.setDate1(usQDTO.getDate1());
        usQEntity.setDate2(usQDTO.getDate2());

        return usQEntity;
    }
}

