package com.refactoring.rekall.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.refactoring.rekall.dto.CartDTO;
import com.refactoring.rekall.dto.ProductDTO;
import com.refactoring.rekall.dto.UserDTO;
import com.refactoring.rekall.entity.CartEntity;
import com.refactoring.rekall.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.codehaus.groovy.transform.SourceURIASTTransformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<CartDTO> findUserId(String loginId) {
        List<CartEntity> cartEntityList = cartRepository.findAllByUserEntityUserIdOrderByCartIdDesc(loginId);
        List<CartDTO> cartDTOList = new ArrayList<>();
        for (CartEntity cartEntity : cartEntityList) {
            if (cartEntity != null) {
                cartDTOList.add(CartDTO.toCartDTO(cartEntity));
            }
        }
        return cartDTOList;
    }

    //  ----------------- ★ userid로 제일 마지막 cart 가져오기 ★ ------------------------------------------------------------
    public CartDTO findCart(String loginId) {
        CartEntity cartEntity = cartRepository.findUserEntityUserId(loginId);

        return  CartDTO.toCartDTO(cartEntity);
    }
    //  ----------------- ★ cartId로 cartDTO 가져오기 ★ ------------------------------------------------------------
    public CartDTO getCartDTO(Integer cartId) {
        Optional<CartEntity> cartEntity = cartRepository.findById(cartId);
        CartDTO cartDTO = CartDTO.toCartDTO(cartEntity.get());

        return  cartDTO;
    }

    //  --------- ★ cart 저장 ★ ----- -------------------------------------------------------
    public void saveCart(String loginId, CartDTO cart, Integer productId, Integer amount) throws Exception {

        UserDTO userDTO = userService.findByUserID(loginId);
        ProductDTO productDTO = productService.findByProductId(productId);

        if(amount != 0) {
            cart.setAmount(amount);
        }

        cart.setUserDTO(userDTO);
        cart.setProductDTO(productDTO);
        cart.setPrice(cart.getAmount()*productDTO.getPrice());
        cartRepository.save(CartEntity.toCartEntity(cart));
    }
    //   ★ 카트 _ 상세이미지 저장 ★ -----------------------------------------------------------------

    public void saveImag(CartDTO cartDTO, MultipartFile file) throws Exception {

        detailImage(cartDTO, cartDTO.getProductDTO().getProductId(), file);

    }

    //   ★ 카트 _ 상세이미지 저장 ★ -----------------------------------------------------------------
    public void detailImage(CartDTO cartDTO, Integer productId, MultipartFile file) throws Exception {

        String saveName = "";
        String str = cartDTO.getProductDTO().getProductId() + "";

        if (file != null) {
            saveName = imageService.saveImg("cart", cartDTO.getUserDTO().getUserId(), str, file);
            System.out.println(saveName);
            cartDTO.setOption_image(saveName);
            cartRepository.save(CartEntity.toCartEntity(cartDTO));
        }
    }
    
    //   ★ 카트 삭제 ★ -----------------------------------------------------------------
    public void delCartIds(String[] cartIds) {
        for (String i : cartIds) {
            i = i.replace("[", "").replace("\"", "").replace("]", "");
            delCartId(Integer.parseInt(i));
        }
    }
    //   ★ 카트 삭제 ★ -----------------------------------------------------------------
    public void delCartId(Integer cartId) {
        cartRepository.deleteById(cartId);
    }

    //   ★ 카트 수정 ★ -----------------------------------------------------------------
    public void changeCart(Integer cartId, Integer amount) {
        Optional<CartEntity> opcartEntity = cartRepository.findById(cartId);
        CartEntity cartEntity = new CartEntity();
        if(opcartEntity.isPresent()) cartEntity = opcartEntity.get();
        cartEntity.setAmount(amount);
        cartRepository.save(cartEntity);
    }

//   ★ 카트 --> order로 이동 ★ ------------------------------------------------------------------------------------------
    public List<CartDTO> cartIdList(String[] cartIds) {
        List<CartDTO> cartDTOList = new ArrayList<>();
        for(String i : cartIds) {
            i = i.replace("[","").replace("\"","").replace("]","");
            Optional<CartEntity> cartEntity = cartRepository.findById(Integer.parseInt(i));
            if(cartEntity.isPresent()) cartDTOList.add(CartDTO.toCartDTO(cartEntity.get()));
        }
        return cartDTOList;
    }
    
//   ★ String --> cartDTO로 변환 ★ ------------------------------------------------------------------------------------------

    public CartDTO convertDTO(String[] strings) {

        JsonObject jsonObject;
        String json = "";
        for(int i = 0 ; i < strings.length; i++) {
            if(i == 0 ) {
                strings[i] = strings[i].replace("[", "{");
                json += strings[i];
            } else if(i == strings.length-1) {
                strings[i] = strings[i].replace("]", "}");
                json += "," + strings[i];
            }else json +=  ","+strings[i];
        }

        Gson gson = new Gson();
        CartDTO cartDTO = gson.fromJson(json, CartDTO.class);

        return cartDTO;
    }
}




//  --------- ★ 주문페이지 : user 정보 넘겨주기 ★ ----- -------------------------------------------------------
/*    public UserDTO findUserId(List<CartDTO> cartDTOList) {

        UserDTO userDTO;
        return  userDTO;
    }*/

