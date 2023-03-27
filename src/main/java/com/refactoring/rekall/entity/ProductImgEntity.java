package com.refactoring.rekall.entity;

import com.refactoring.rekall.dto.ProductImgDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Transactional
@Table(name = "product_img")
public class ProductImgEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productimgId; // ▷▶ id _ 자동 count _ 상품이미지번호


// -------- ▷▶ productImg 가 외래키로 가져오는 Entity ----------------------------------------------
    @ManyToOne
    @JoinColumn(name = "productId")
    private ProductEntity productEntity;  //  ▷▶ 상품번호
// -------------------------------------------------------------------------------------------

    @Column(nullable = false, length = 100)
    private String imgName; // ▷▶ 파일경로

// -------------- ▷▶ DTO -> Entity --------------------------------------------------------
    public static ProductImgEntity toSetProductImgEntity(ProductImgDTO productImgDTO) {
        if(productImgDTO == null) return  null;
        ProductImgEntity productImgEntity = new ProductImgEntity();

        productImgEntity.setProductimgId(productImgDTO.getProductimgId());
        productImgEntity.setProductEntity(ProductEntity.toProductEntity(productImgDTO.getProductDTO()));
        productImgEntity.setImgName(productImgDTO.getImgName());

        return productImgEntity;
    }
}
