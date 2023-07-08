package com.refactoring.rekall.dto;


import com.refactoring.rekall.entity.ProductQEntity;
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductQDTO {

    private Integer productqId; // ▷▶ id _ 자동 count _ 상품문의번호

    // -------- ▷▶ productq 가 외래키로 가져오는 DTO ----------------------------------------------
    private UserDTO userDTO; // ▷▶ 유저Id
    private ProductDTO productDTO; // ▷▶ 상품번호
// -------------------------------------------------------------------------------------------

    private String title;  // ▷▶ 문의 제목
    private String content;  // ▷▶ 내용
    private String comment; // ▷▶ 답글
    private LocalDateTime date1 = LocalDateTime.now(); // ▷▶ 문의글 날짜
    private LocalDateTime date2 = LocalDateTime.now(); // ▷▶ 답글 날짜

// -------------- ▷▶ Entity -> DTO ---------------------------------------------------------
    public static ProductQDTO toProductQDTO(ProductQEntity productQEntity) {
        if(productQEntity == null) return null;
        ProductQDTO productQDTO = new ProductQDTO();

        productQDTO.setProductqId(productQEntity.getProductqId());
        productQDTO.setUserDTO(UserDTO.toUserDTO(productQEntity.getUserEntity()));
        productQDTO.setProductDTO(ProductDTO.toProductDTO(productQEntity.getProductEntity()));
        productQDTO.setTitle(productQEntity.getTitle());
        productQDTO.setContent(productQEntity.getContent());
        productQDTO.setComment(productQEntity.getComment());
        productQDTO.setDate1(productQEntity.getDate1());
        productQDTO.setDate2(productQEntity.getDate2());

        return productQDTO;
    }
}
