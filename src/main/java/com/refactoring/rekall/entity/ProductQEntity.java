package com.refactoring.rekall.entity;

import com.refactoring.rekall.dto.ProductQDTO;
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
@Table(name = "product_q")
public class ProductQEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productqId; // ▷▶ id _ 자동 count _ 상품문의번호

// -------- ▷▶ productq 가 외래키로 가져오는 Entity ----------------------------------------------
    @JoinColumn(name = "userId")
    @ManyToOne
    private UserEntity userEntity; // ▷▶ 유저Id
    @ManyToOne
    @JoinColumn(name = "productId")
    private ProductEntity productEntity; // ▷▶ 상품번호
// -------------------------------------------------------------------------------------------

    @Column(nullable = false, length = 50)
    private String title;  // ▷▶ 문의 제목
    @Column(nullable = false, length = 500)
    private String content;  // ▷▶ 내용
    @Column(length = 500)
    private String comment; // ▷▶ 답글
    @CreationTimestamp // 생성일시
    private LocalDateTime date1 = LocalDateTime.now(); // ▷▶ 날짜
    @CreationTimestamp // 답글 생성일시
    private LocalDateTime date2 = LocalDateTime.now(); // ▷▶ 답글날짜

// -------------- ▷▶ DTO -> Entity --------------------------------------------------------
    public static ProductQEntity toProductQEntity(ProductQDTO productQDTO) {
        if(productQDTO == null) return null;

        ProductQEntity productQEntity = new ProductQEntity();

        productQEntity.setProductqId(productQDTO.getProductqId());
        productQEntity.setUserEntity(UserEntity.toUserEntity(productQDTO.getUserDTO()));
        productQEntity.setProductEntity(ProductEntity.toProductEntity(productQDTO.getProductDTO()));
        productQEntity.setTitle(productQDTO.getTitle());
        productQEntity.setContent(productQDTO.getContent());
        productQEntity.setComment(productQDTO.getComment());
        productQEntity.setDate1(productQDTO.getDate1());
        productQEntity.setDate2(LocalDateTime.now());

        return productQEntity;
    }
}
