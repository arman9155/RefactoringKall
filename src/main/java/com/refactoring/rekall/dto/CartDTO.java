package com.refactoring.rekall.dto;

import com.refactoring.rekall.entity.CartEntity;
import com.refactoring.rekall.entity.ProductEntity;
import com.refactoring.rekall.entity.UserEntity;
import lombok.*;

import javax.persistence.*;

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {

    private Integer cartId; // ▷▶  ID -> seq _ 장바구니번호

    // -------- ▷▶ cart 가 외래키로 사용하는 DTO ----------------------------------------------
    private ProductDTO productDTO; // ▷▶ 상품번호
    private UserDTO userDTO; // ▷▶ 유저ID
// -------------------------------------------------------------------------------------------

    private String option_sheet; // ▷▶ 시트 옵션
    private String option_shape; // ▷▶ 모양 옵션
    private String option_cream; // ▷▶ 크림 색상 옵션
    private String option_lettering; // ▷▶ 레터링 옵션
    private String option_size; // ▷▶ 사이즈 옵션
    private String option_image; // ▷▶ 이미지 옵션
    private Integer amount; // ▷▶ 개수
    private Integer price; // ▷▶ 가격
    private String request; // ▷▶ 요청사항

// -------------- ▷▶ Entity -> DTO ---------------------------------------------------------
    public static CartDTO toCartDTO(CartEntity cartEntity) {
        if(cartEntity == null) return null;
        CartDTO cartDTO = new CartDTO();

        cartDTO.setCartId(cartEntity.getCartId());
        cartDTO.setProductDTO(ProductDTO.toProductDTO(cartEntity.getProductEntity()));
        cartDTO.setUserDTO(UserDTO.toUserDTO(cartEntity.getUserEntity()));
        cartDTO.setOption_sheet(cartEntity.getOption_sheet());
        cartDTO.setOption_shape(cartEntity.getOption_shape());
        cartDTO.setOption_cream(cartEntity.getOption_cream());
        cartDTO.setOption_lettering(cartEntity.getOption_lettering());
        cartDTO.setOption_size(cartEntity.getOption_size());
        cartDTO.setOption_image(cartEntity.getOption_image());
        cartDTO.setAmount(cartEntity.getAmount());
        cartDTO.setPrice(cartEntity.getPrice());
        cartDTO.setRequest(cartEntity.getRequest());

        return cartDTO;
    }

}
