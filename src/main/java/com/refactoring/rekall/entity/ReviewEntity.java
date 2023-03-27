package com.refactoring.rekall.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.refactoring.rekall.dto.ReviewDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name = "review")
public class ReviewEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewId; // ▷▶ id _ 자동 count

// -------- ▷▶   reviewId를 사용하는 Entity ----------------------------------------------
    @OneToMany(mappedBy = "reviewEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ReviewCmtEntity> reviewCmtEntities = new ArrayList<>();

// -------- ▷▶   reviewId가 외래키로 가져오는 Entity ----------------------------------------------
    @JoinColumn(name = "userId")
    @ManyToOne
    private UserEntity userEntity; // ▷▶ 유저ID
    @ManyToOne
    @JoinColumn(name = "productId")
    private ProductEntity productEntity; // ▷▶ 상품ID
    @ManyToOne
    @JoinColumn(name = "odetailId")
    private OrderDetailEntity orderDetailEntity; // ▷▶ 주문상세번호
// -------------------------------------------------------------------------------------------

    @Column(nullable = false, length = 50)
    private String title; // ▷▶ 후기제목
    @Column(nullable = false, length = 500)
    private String content; // ▷▶ 후기
    @Column(nullable = false, length = 2)
    private Float star; // ▷▶ 별점
    @CreationTimestamp // 생성일시
    private LocalDateTime date = LocalDateTime.now(); // ▷▶ 후기작성일
    @Column(length=100)
    private String image1; // ▷▶ 후기이미지1
    @Column(length=100)
    private String image2; // ▷▶ 후기이미지2

// -------------- ▷▶ DTO -> Entity --------------------------------------------------------
    public static ReviewEntity toReviewEntity(ReviewDTO reviewDTO) {
        if(reviewDTO == null) return null;
        ReviewEntity reviewEntity = new ReviewEntity();

        reviewEntity.setReviewId(reviewDTO.getReviewId());
        reviewEntity.setUserEntity(UserEntity.toUserEntity(reviewDTO.getUserDTO()));
        reviewEntity.setProductEntity(ProductEntity.toProductEntity(reviewDTO.getProductDTO()));
        reviewEntity.setOrderDetailEntity(OrderDetailEntity.toOrderDetailEntity(reviewDTO.getOrderDetailDTO()));
        reviewEntity.setTitle(reviewDTO.getTitle());
        reviewEntity.setContent(reviewDTO.getContent());
        reviewEntity.setStar(reviewDTO.getStar());
        reviewEntity.setDate(reviewDTO.getDate());
        reviewEntity.setImage1(reviewDTO.getImage1());
        reviewEntity.setImage2(reviewDTO.getImage2());

        return reviewEntity;
    }
}
