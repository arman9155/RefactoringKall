package com.refactoring.rekall.dto;

import com.refactoring.rekall.entity.ProductEntity;
import com.refactoring.rekall.entity.UserEntity;
import com.refactoring.rekall.entity.WishListEntity;
import lombok.*;
import org.apache.catalina.User;

import java.time.LocalDateTime;

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WishListDTO {

    private Integer wishlistId; // ▷▶ id _ 자동 count

    // -------- ▷▶  wishlist 가 외래키로 가져오는 DTO ----------------------------------------------
    private ProductDTO productDTO; // ▷▶ 상품번호
    private UserDTO userDTO; // ▷▶ 유저Id
// -------------------------------------------------------------------------------------------

    private LocalDateTime date= LocalDateTime.now(); // ▷▶ 등록일

// -------------- ▷▶ DTO -> Entity --------------------------------------------------------
    public static WishListDTO toWishListDTO(WishListEntity wishListEntity) {
        if(wishListEntity == null) return null;
        WishListDTO wishListDTO = new WishListDTO();

        wishListDTO.setWishlistId(wishListEntity.getWishlistId());
        wishListDTO.setProductDTO(ProductDTO.toProductDTO(wishListEntity.getProductEntity()));
        wishListDTO.setUserDTO(UserDTO.toUserDTO(wishListEntity.getUserEntity()));
        wishListDTO.setDate(wishListEntity.getDate());

        return wishListDTO;
    }
}
