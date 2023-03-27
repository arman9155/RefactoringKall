package com.refactoring.rekall.controller;

import com.refactoring.rekall.dto.ProductDTO;
import com.refactoring.rekall.service.ProductService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
@Getter
public class MainController {

    @Autowired
    ProductService productService;

//  ------------------------------------- ★ Product List ★ ------------------------------------------------------------
    @GetMapping(value = {"", "main"})
    public ModelAndView main() {
        ModelAndView modelAndView = new ModelAndView();
        List<ProductDTO> productDTOList = productService.findAll();
        modelAndView.addObject("recommendProduct", productDTOList);
        modelAndView.setViewName("pages/main.html");

        return modelAndView;
    }

//  ------------------------------------- ★ Community ★ ---------------------------------------------------------------

    @GetMapping("road") // 찾아오시는 길
    public ModelAndView road() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/community/road.html");
        return modelAndView;
    }


    @GetMapping("review") // 후기
    public ModelAndView review() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/community/review.html");
        return modelAndView;
    }

//  ------------------------------------- ★ login ★ -------------------------------------------------------------------
    @GetMapping("login") // 로그인
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/login/login.html");
        return modelAndView;
    }

    @PostMapping("login") // 로그인
    public ModelAndView loginOK() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/main.html");
        return modelAndView;
    }

    @GetMapping("join") // 로그인
    public ModelAndView join() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/login/join.html");
        return modelAndView;
    }

//  ------------------------------------- ★ My Page ★ -----------------------------------------------------------------

    @GetMapping("mypage") // 로그인
    public ModelAndView mypage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/mypage/mypage.html");
        return modelAndView;
    }


//  ------------------------------------- ★ Cart / Order ★ -----------------------------------------------------------------
    @GetMapping("cart") // 장바구니
    public ModelAndView cart() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/order/cart.html");
        return modelAndView;
    }

    @PostMapping("order") // 장바구니 -> 주문으로
    public ModelAndView order() {
        ModelAndView modelAndView = new ModelAndView();
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



