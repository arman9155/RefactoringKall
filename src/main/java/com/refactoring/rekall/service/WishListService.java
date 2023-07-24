package com.refactoring.rekall.service;

import com.refactoring.rekall.dto.ProductDTO;
import com.refactoring.rekall.dto.UserDTO;
import com.refactoring.rekall.dto.WishListDTO;
import com.refactoring.rekall.entity.UserEntity;
import com.refactoring.rekall.entity.WishListEntity;
import com.refactoring.rekall.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;



@Service
@Transactional
@RequiredArgsConstructor
public class WishListService {
    @Autowired
    WishListRepository wishListRepository;
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;

//  ------------------------------------- ★ 로그인 id 별 wishList 뽑기★ ------------------------------------------------------------
    public List<WishListDTO> findWishList(String userId) {
        if(userId != null) {
            List<WishListEntity> wishList = wishListRepository.findByUserEntityUserIdOrderByDateDesc(userId);
            List<WishListDTO> wishListDTOList = new ArrayList<>();
            for (WishListEntity wishListEntity : wishList) {
                if (wishListEntity != null) {
                    wishListDTOList.add(WishListDTO.toWishListDTO(wishListEntity));
                }
            }
            return wishListDTOList;
        }
        return new ArrayList<>(0);
    }

//  ------------------------------------- ★ wishList 저장★ ------------------------------------------------------------
    public void getWishList(Integer productId, String loginId) {
        WishListDTO wishListDTO = new WishListDTO();
        UserDTO userDTO = userService.findByUserID(loginId); // 회원정보 찾기
        ProductDTO productDTO = productService.findByProductId(productId); // 상품

        List<WishListDTO> wishListDTOList = findWishList(loginId); // 기존 회원의 wishList
         if (!wishListDTOList.isEmpty()) {
             boolean answer = true;
             for(WishListDTO wishList : wishListDTOList) {
                 if (wishList.getProductDTO().getProductId() != productDTO.getProductId()) {//기존 list에 없을 때 true
                     answer = true;
                     wishListDTO.setProductDTO(productDTO);
                     wishListDTO.setUserDTO(userDTO);
                 }   else { //  혹 중간에 있으면 false로 변환하고 for문 끝냄
                     answer = false;
                     break;
                 }
             }
             if(answer)
                 wishListRepository.save(WishListEntity.toWishListEntity(wishListDTO));
         } else { // null이면 저장바로 하면 됨
             wishListDTO.setProductDTO(productDTO);
             wishListDTO.setUserDTO(userDTO);
             wishListRepository.save(WishListEntity.toWishListEntity(wishListDTO));
         }
    }

//  ------------------------------------- ★ wishList 삭제★ ------------------------------------------------------------
    public void delWishList(Integer productId, String userId) {

      Integer wishListId   = wishListRepository.findWishListId(productId, userId);

      if(wishListId != null) wishListRepository.deleteById(wishListId);
    }
}
