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


//  ------------------------------------- ★ My Page ★ -----------------------------------------------------------------

    @GetMapping("mypage") // 마이페이지
    public ModelAndView mypage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/mypage/mypage.html");
        return modelAndView;
    }

}



