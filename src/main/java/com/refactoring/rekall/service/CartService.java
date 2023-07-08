package com.refactoring.rekall.service;

import com.refactoring.rekall.dto.CartDTO;
import com.refactoring.rekall.dto.ProductImgDTO;
import com.refactoring.rekall.dto.UserDTO;
import com.refactoring.rekall.entity.CartEntity;
import com.refactoring.rekall.entity.ProductImgEntity;
import com.refactoring.rekall.repository.CartRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;
    @Autowired
    ImageService imageService;


    //  ----------------- ★ userid에 맞는 cart List ★ ------------------------------------------------------------
//  로그인 구현이 아직이라 확인할 수 없음
    public List<CartDTO> findUserId(String loginId) {
        List<CartEntity> cartEntityList = cartRepository.findAllByUserEntityUserId(loginId);
        List<CartDTO> cartDTOList = new ArrayList<>();
        for (CartEntity cartEntity : cartEntityList) {
            if (cartEntity != null) {
                cartDTOList.add(CartDTO.toCartDTO(cartEntity));
            }
        }
        return cartDTOList;
    }

    //  --------- ★ cart 저장 ★ ----- -------------------------------------------------------
    public void saveCart(String loginId, List<CartDTO> cartDTO, MultipartFile[] files, Integer productId) throws Exception {
        int index = 0;
        for (CartDTO cart : cartDTO) {
            cart.setUserDTO(userService.findByUserID(loginId));
            cart.setProductDTO(productService.findByProductId(productId));
            if (cart.getProductDTO().getCategoryDTO().getCategoryId().equals("custom")) {
                detailImage(cart, productId, files, index++);
            }
            cartRepository.save(CartEntity.toCartEntity(cart));
        }
    }

    //   ★ 상품관리 _ 상세이미지 저장 ★ -----------------------------------------------------------------
    public void detailImage(CartDTO cartDTO, Integer productId, MultipartFile[] files, Integer index) throws Exception {

        String id = "0";
        if (cartRepository.findId() == null) id = "1";
        else id = cartRepository.findId() + 1 + "";  // 기존 img가 있는 경우 최종 + 1 로 설정

        String saveName = "";
        String str = cartDTO.getProductDTO().getProductId() + "";

        if (files != null) {
            if (!files[index].isEmpty()) {
                saveName = imageService.saveImg("cart", cartDTO.getUserDTO().getUserId(), str, files[index]);
                cartDTO.setOption_image(saveName);
            }
        }
    }
}
//  --------- ★ 주문페이지 : user 정보 넘겨주기 ★ ----- -------------------------------------------------------
/*    public UserDTO findUserId(List<CartDTO> cartDTOList) {

        UserDTO userDTO;
        return  userDTO;
    }*/

