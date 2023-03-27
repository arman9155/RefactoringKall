package com.refactoring.rekall.entity;

import com.refactoring.rekall.dto.CartDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name = "cart")
public class CartEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartId; // ▷▶  ID -> seq _ 장바구니번호

// -------- ▷▶ cart 가 외래키로 사용하는 Entity ----------------------------------------------
    @JoinColumn(name = "productId")
    @ManyToOne
    private ProductEntity productEntity; // ▷▶ 상품번호
    @JoinColumn(name = "userId")
    @ManyToOne
    private UserEntity userEntity; // ▷▶ 유저ID
// -------------------------------------------------------------------------------------------

    @Column(length = 20)
    private String option_sheet; // ▷▶ 시트 옵션
    @Column(length = 20)
    private String option_shape; // ▷▶ 모양 옵션
    @Column(length = 20)
    private String option_cream; // ▷▶ 크림 색상 옵션
    @Column(length = 30)
    private String option_lettering; // ▷▶ 레터링 옵션
    @Column(length = 20)
    private String option_size; // ▷▶ 사이즈 옵션
    @Column(length = 100)
    private String option_image; // ▷▶ 이미지 옵션
    @Column(nullable = false, length=3)
    private Integer amount; // ▷▶ 개수
    @Column(nullable = false, length = 8)
    private Integer price; // ▷▶ 가격

// ------------ ▷▶ 예비컬럼----------------------------------------------------------------------------
    @Column(length = 50)
    private String tmp_1;
    @Column(length = 50)
    private String tmp_2;

// -------------- ▷▶ DTO -> Entity --------------------------------------------------------
    public static CartEntity toCartEntity(CartDTO cartDTO) {
        if(cartDTO == null) return null;

        CartEntity cartEntity = new CartEntity();

        cartEntity.setCartId(cartDTO.getCartId());
        cartEntity.setProductEntity(ProductEntity.toProductEntity(cartDTO.getProductDTO()));
        cartEntity.setUserEntity(UserEntity.toUserEntity(cartDTO.getUserDTO()));
        cartEntity.setOption_sheet(cartDTO.getOption_sheet());
        cartEntity.setOption_shape(cartDTO.getOption_shape());
        cartEntity.setOption_cream(cartDTO.getOption_cream());
        cartEntity.setOption_lettering(cartDTO.getOption_lettering());
        cartEntity.setOption_size(cartDTO.getOption_size());
        cartEntity.setOption_image(cartDTO.getOption_image());
        cartEntity.setAmount(cartDTO.getAmount());
        cartEntity.setPrice(cartDTO.getPrice());

        return cartEntity;
    }
}
