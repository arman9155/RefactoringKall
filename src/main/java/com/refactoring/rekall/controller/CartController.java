package com.refactoring.rekall.controller;

import com.refactoring.rekall.dto.CartDTO;
import com.refactoring.rekall.dto.UsQDTO;
import com.refactoring.rekall.dto.UserDTO;
import com.refactoring.rekall.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
public class CartController {

    @Autowired
    CartService cartService;

    //  ------------------------------------- ★ Cart / Order 리스트★ -----------------------------------------------------------------

    @GetMapping("cart") // 장바구니 => userid에 맞는 리스트 뽑
    public ModelAndView cart(@SessionAttribute(name = "loginId", required = false) String loginId,
                             @SessionAttribute(name = "userRole", required = false) String userRole) {
        ModelAndView modelAndView = new ModelAndView();
        List<CartDTO> cartDTOList = cartService.findUserId(loginId);
        // id 전달 받아서 id 넣으면 id 에 맞게 search
        modelAndView.addObject("loginId", loginId);
        modelAndView.addObject("userRole", userRole);
        modelAndView.addObject("page", "cart");
        modelAndView.addObject("cartList", cartDTOList);
        modelAndView.setViewName("pages/order/cart.html");
        return modelAndView;
    }

    @PostMapping("/cart/{id}") // 장바구니 => userid에 맞는 리스트 뽑
    public ModelAndView saveCart(@PathVariable("id") String loginId,
                                 @RequestParam("userRole") String userRole,
                                 @RequestParam("DTO") List<CartDTO> cartDTO,
                                 @RequestParam(name = "files", required = false) MultipartFile[] files,
                                 @RequestParam("productId") Integer productId) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        cartService.saveCart(loginId, cartDTO, files, productId);
        List<CartDTO> cartDTOList = cartService.findUserId(loginId);

        modelAndView.addObject("loginId", loginId);
        modelAndView.addObject("userRole", userRole);
        modelAndView.addObject("page", "cart");
        modelAndView.addObject("cartDTOList", cartDTOList);
        modelAndView.setViewName("pages/order/cart.html");
        return modelAndView;
    }

    @PostMapping("toOrder") // 장바구니 DTOLIst-> 주문으로
    public ModelAndView order(@SessionAttribute(name ="loginId", required = false) String loginId,
                              @SessionAttribute(name ="userRole", required = false) String userRole,
                              @ModelAttribute("cartList") List<CartDTO> cartDTOList, MultipartFile imgFile) {
        ModelAndView modelAndView = new ModelAndView();

/*   UserDTO userDTO = cartService.findUserId(cartDTOList);
     로그인 땜시 막아둠*/

/*  modelAndView.addObject("user",userDTO);*/

        modelAndView.addObject("loginId", loginId);
        modelAndView.addObject("userRole", userRole);
        modelAndView.addObject("cartList",cartDTOList);
        modelAndView.setViewName("pages/order/order.html");
        return modelAndView;
    }
    @PostMapping("complete") // 주문 -> 결제완료
    public ModelAndView complete() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/order/complete.html");
        return modelAndView;
    }
}
