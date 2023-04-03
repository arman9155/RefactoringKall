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

//  ------------------------------------- ★ 로그인 id 별 wishList 뽑기★ ------------------------------------------------------------
    public List<WishListDTO> findWishList(String userId) {
        List<WishListEntity> wishList = wishListRepository.findByUserEntityUserId(userId);
        List<WishListDTO> wishListDTOList = new ArrayList<>();
        for(WishListEntity wishListEntity : wishList) {
            if(wishListEntity != null) {
                wishListDTOList.add(WishListDTO.toWishListDTO(wishListEntity));
            }
        }

        return wishListDTOList;
    }

//  ------------------------------------- ★ wishList 저장★ ------------------------------------------------------------
    public void getWishList(ProductDTO productDTO, String loginId) {
        WishListDTO wishListDTO = new WishListDTO();
        UserDTO userDTO = userService.findByUserID(loginId); // 회원정보 찾기
        wishListDTO.setProductDTO(productDTO);
        wishListDTO.setUserDTO(userDTO);

        wishListRepository.save(WishListEntity.toWishListEntity(wishListDTO));
    }


}
