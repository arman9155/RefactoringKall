package com.refactoring.rekall.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.refactoring.rekall.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
/*@Transactional*/
@Table(name = "product")
public class ProductEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId; //  ▷▶ id _ 자동 count _ 상품번호

// -------- ▷▶  productId 를 사용하는 Entity ----------------------------------------------
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ProductImgEntity> productImgEntities = new ArrayList<>();
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OrderDetailEntity> orderDetailEntities = new ArrayList<>();
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ReviewEntity> reviewEntities = new ArrayList<>();
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CartEntity> cartEntities = new ArrayList<>();
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<WishListEntity> wishListEntities = new ArrayList<>();


// -------- ▷▶  product가 외래키로 가져오는 Entity ----------------------------------------------
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private CategoryEntity categoryEntity;  //  ▷▶ 카테고리Id
// -------------------------------------------------------------------------------------------

    @Column(nullable = false, length = 50)
    private String name; //  ▷▶ 상품명
    @Column(nullable = false, length = 8)
    private Integer price; //  ▷▶ 가격
    @Column(nullable = false, length = 3)
    private Integer amount; //  ▷▶ 재고
    @Column(nullable = false, length = 1000)
    private String info; //  ▷▶ 상세 설명
    @CreationTimestamp // 생성일시
    private LocalDateTime date = LocalDateTime.now(); //  ▷▶ 등록날짜
    @ColumnDefault("0")
    @Column(length = 2)
    private Float star; //  ▷▶ 별점
    @Column(length = 100)
    private String tag; //  ▷▶ 태그
    @Column(length = 100)
    private String image = "example"; //  ▷▶ 메인 이미지
    @ColumnDefault("0")
    @Column(length = 5)
    private Integer cnt; //  ▷▶ 조회수

// ------------ ▷▶ 예비컬럼----------------------------------------------------------------------------
    @Column(length = 50)
    private String tmp_1;
    @Column(length = 50)
    private String tmp_2;


// -------------- DTO -> Entity ----------------------------------------------------------------
    public static ProductEntity toProductEntity(ProductDTO productDTO) {
        if(productDTO == null) return null;
        ProductEntity productEntity = new ProductEntity();

        productEntity.setProductId(productDTO.getProductId());
        productEntity.setCategoryEntity(CategoryEntity.toCategoryEntity(productDTO.getCategoryDTO()));
        productEntity.setName(productDTO.getName());
        productEntity.setPrice(productDTO.getPrice());
        productEntity.setAmount(productDTO.getAmount());
        productEntity.setInfo(productDTO.getInfo());
        productEntity.setDate(productDTO.getDate());
        productEntity.setStar(productDTO.getStar());
        productEntity.setTag(productDTO.getTag());
        productEntity.setImage(productDTO.getImage());
        productEntity.setCnt(productDTO.getCnt());

        return productEntity;
    }
}
