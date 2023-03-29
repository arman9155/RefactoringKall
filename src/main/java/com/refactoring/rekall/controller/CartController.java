package com.refactoring.rekall.controller;

import com.refactoring.rekall.dto.CartDTO;
import com.refactoring.rekall.dto.UsQDTO;
import com.refactoring.rekall.dto.UserDTO;
import com.refactoring.rekall.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
public class CartController {

    @Autowired
    CartService cartService;

    //  ------------------------------------- ★ Cart / Order 리스트★ -----------------------------------------------------------------

/*    @GetMapping("cart") // 장바구니 => userid에 맞는 리스트 뽑
    public ModelAndView cart() {
        ModelAndView modelAndView = new ModelAndView();
        List<CartDTO> cartDTOList = cartService.findUserId();
        // id 전달 받아서 id 넣으면 id 에 맞게 search 되겡
        modelAndView.addObject("cartDTOList", cartDTOList);
        modelAndView.setViewName("pages/order/cart.html");
        return modelAndView;
    }*/

    @PostMapping("order") // 장바구니 DTOLIst-> 주문으로
    public ModelAndView order(@ModelAttribute("cartList") List<CartDTO> cartDTOList) {
        ModelAndView modelAndView = new ModelAndView();
     /*   UserDTO userDTO = cartService.findUserId(cartDTOList);
     로그인 땜시 막아둠*/

      /*  modelAndView.addObject("user",userDTO);*/
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
