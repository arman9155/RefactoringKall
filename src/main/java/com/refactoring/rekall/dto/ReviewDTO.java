package com.refactoring.rekall.dto;

import com.refactoring.rekall.entity.*;
import lombok.*;
import java.time.LocalDateTime;


@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    private Integer reviewId; // ▷▶ id _ 자동 count

    // -------- ▷▶   reviewId가 외래키로 가져오는 DTO ----------------------------------------------
    private UserDTO userDTO; // ▷▶ 유저ID
    private ProductDTO productDTO; // ▷▶ 상품ID
    private OrderDetailDTO orderDetailDTO; // ▷▶ 주문상세번호
// -------------------------------------------------------------------------------------------

    private String title; // ▷▶ 후기제목
    private String content; // ▷▶ 후기
    private Float star; // ▷▶ 별점
    private LocalDateTime date = LocalDateTime.now(); // ▷▶ 후기작성일
    private String image1; // ▷▶ 후기이미지1
    private String image2; // ▷▶ 후기이미지2

// -------------- ▷▶ Entity -> DTO ---------------------------------------------------------
    public static ReviewDTO toReviewDTO(ReviewEntity reviewEntity) {
        if(reviewEntity == null) return null;
        ReviewDTO reviewDTO = new ReviewDTO();

        reviewDTO.setReviewId(reviewEntity.getReviewId());
        reviewDTO.setUserDTO(UserDTO.toUserDTO(reviewEntity.getUserEntity()));
        reviewDTO.setProductDTO(ProductDTO.toProductDTO(reviewEntity.getProductEntity()));
        reviewDTO.setOrderDetailDTO(OrderDetailDTO.toOrderDetailDTO(reviewEntity.getOrderDetailEntity()));
        reviewDTO.setTitle(reviewEntity.getTitle());
        reviewDTO.setContent(reviewEntity.getContent());
        reviewDTO.setStar(reviewEntity.getStar());
        reviewDTO.setDate(reviewEntity.getDate());
        reviewDTO.setImage1(reviewEntity.getImage1());
        reviewDTO.setImage2(reviewEntity.getImage2());

        return reviewDTO;
    }
}
