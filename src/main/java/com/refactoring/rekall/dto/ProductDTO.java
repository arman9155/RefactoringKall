package com.refactoring.rekall.dto;

import com.refactoring.rekall.entity.*;
import lombok.*;
import java.time.LocalDateTime;


@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Integer productId; //  ▷▶ id _ 자동 count _ 상품번호

// -------- ▷▶  product가 외래키로 가져오는 DTO ----------------------------------------------
    private CategoryDTO categoryDTO;  //  ▷▶ 카테고리Id
// -------------------------------------------------------------------------------------------

    private String name; //  ▷▶ 상품명
    private Integer price; //  ▷▶ 가격
    private Integer amount; //  ▷▶ 재고
    private String info; //  ▷▶ 상세 설명
    private LocalDateTime date = LocalDateTime.now(); //  ▷▶ 등록날짜
    private Float star; //  ▷▶ 별점
    private String tag; //  ▷▶ 태그
    private String image = "example"; //  ▷▶ 메인 이미지
    private Integer cnt; //  ▷▶ 조회수

// -------------- ▷▶ Entity -> DTO ---------------------------------------------------------
    public static ProductDTO toProductDTO(ProductEntity productEntity) {
        if(productEntity == null) return null;
        ProductDTO productDTO = new ProductDTO();

        productDTO.setProductId(productEntity.getProductId());
        productDTO.setCategoryDTO(CategoryDTO.toCategoryDTO(productEntity.getCategoryEntity()));
        productDTO.setName(productEntity.getName());
        productDTO.setPrice(productEntity.getPrice());
        productDTO.setAmount(productEntity.getAmount());
        productDTO.setInfo(productEntity.getInfo());
        productDTO.setDate(productEntity.getDate());
        productDTO.setStar(productEntity.getStar());
        productDTO.setTag(productEntity.getTag());
        productDTO.setImage(productEntity.getImage());
        productDTO.setCnt(productEntity.getCnt());

        return productDTO;
    }
}
