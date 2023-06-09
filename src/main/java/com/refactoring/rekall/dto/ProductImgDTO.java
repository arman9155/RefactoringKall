package com.refactoring.rekall.dto;

import com.refactoring.rekall.entity.ProductImgEntity;
import lombok.*;


@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductImgDTO {

    private Integer productimgId; // ▷▶ id _ 자동 count _ 상품이미지번호

    // -------- ▷▶ productImg 가 외래키로 가져오는 DTO ----------------------------------------------
    private ProductDTO productDTO;  //  ▷▶ 상품번호
// -------------------------------------------------------------------------------------------

    private String image = "example"; // ▷▶ 파일경로
    private String imageName = "example"; // ▷▶ 파일명

// -------------- ▷▶ Entity -> DTO ---------------------------------------------------------
    public static ProductImgDTO toProductImgDTO(ProductImgEntity productImgEntity) {
        if(productImgEntity == null) return null;
        ProductImgDTO productImgDTO = new ProductImgDTO();

        productImgDTO.setProductimgId(productImgEntity.getProductimgId());
        productImgDTO.setProductDTO(ProductDTO.toProductDTO(productImgEntity.getProductEntity()));
        productImgDTO.setImage(productImgEntity.getImage());
        productImgDTO.setImageName(productImgEntity.getImageName());

        return productImgDTO;
    }
}
