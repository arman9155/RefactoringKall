package com.refactoring.rekall.entity;

import com.refactoring.rekall.dto.WishListDTO;
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
@Table(name = "wish_list")
public class WishListEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer wishlistId; // ▷▶ id _ 자동 count

// -------- ▷▶  wishlist 가 외래키로 가져오는 Entity ----------------------------------------------
    @JoinColumn(name = "productId")
    @ManyToOne
    private ProductEntity productEntity; // ▷▶ 상품번호
    @JoinColumn(name = "userId")
    @ManyToOne
    private UserEntity userEntity; // ▷▶ 유저Id
// -------------------------------------------------------------------------------------------

    @CreationTimestamp // 생성일시
    private LocalDateTime date= LocalDateTime.now(); // ▷▶ 등록일

// -------------- ▷▶ DTO -> Entity --------------------------------------------------------
    public static WishListEntity toWishListEntity(WishListDTO wishListDTO) {
        if (wishListDTO == null) return null;
        WishListEntity wishListEntity = new WishListEntity();

        wishListEntity.setWishlistId(wishListDTO.getWishlistId());
        wishListEntity.setProductEntity(ProductEntity.toProductEntity(wishListDTO.getProductDTO()));
        wishListEntity.setUserEntity(UserEntity.toUserEntity(wishListDTO.getUserDTO()));
        wishListEntity.setDate(wishListDTO.getDate());

        return wishListEntity;
    }
}
