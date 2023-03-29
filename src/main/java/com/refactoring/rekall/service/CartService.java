package com.refactoring.rekall.service;

import com.refactoring.rekall.dto.CartDTO;
import com.refactoring.rekall.dto.UserDTO;
import com.refactoring.rekall.entity.CartEntity;
import com.refactoring.rekall.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Controller
@Transactional
@AllArgsConstructor
public class CartService {

    @Autowired
    CartRepository cartRepository;



//  ----------------- ★ userid에 맞는 cart List ★ ------------------------------------------------------------
//  로그인 구현이 아직이라 확인할 수 없음
/*    public List<CartDTO> findUserId() {
        List<CartEntity> cartEntityList = cartRepository.findUserId();
        List<CartDTO> cartDTOList = new ArrayList<>();
        for(CartEntity cartEntity : cartEntityList) {
            if(cartEntity != null) {
                cartDTOList.add(CartDTO.toCartDTO(cartEntity));
            }
        }
        return cartDTOList;
    }*/

//  --------- ★ 주문페이지 : user 정보 넘겨주기 ★ ------------------------------------------------------------
/*    public UserDTO findUserId(List<CartDTO> cartDTOList) {

        UserDTO userDTO;
        return  userDTO;
    }*/
}
