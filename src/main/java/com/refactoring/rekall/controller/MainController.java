package com.refactoring.rekall.controller;

import com.refactoring.rekall.dto.ProductDTO;
import com.refactoring.rekall.service.ProductService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@Getter
public class MainController {

    @Autowired
    ProductService productService;

//  ------------------------------------- ★ Product List ★ ------------------------------------------------------------
    @GetMapping(value = {"", "main"})
    public ModelAndView main(@SessionAttribute(name ="loginId", required = false) String loginId,
                             @SessionAttribute(name ="userRole", required = false) String userRole) {
        ModelAndView modelAndView = new ModelAndView();
        List<ProductDTO> productDTOS = productService.findAll();
        List<ProductDTO> productDTOList = new ArrayList<>(productDTOS.subList(0, 6));

        modelAndView.addObject("loginId", loginId);
        modelAndView.addObject("userRole", userRole);
        modelAndView.addObject("recommendProduct", productDTOList);
        modelAndView.setViewName("pages/main.html");

        return modelAndView;
    }

//  ------------------------------------- ★ Community ★ ---------------------------------------------------------------

    @GetMapping("road") // 찾아오시는 길
    public ModelAndView road(@SessionAttribute(name ="loginId", required = false) String loginId,
                             @SessionAttribute(name ="userRole", required = false) String userRole) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("loginId", loginId);
        modelAndView.addObject("userRole", userRole);
        modelAndView.setViewName("pages/community/road.html");
        return modelAndView;
    }

//  ------------------------------------- ★ admin 페이지 ★ ---------------------------------------------------------------
    @GetMapping("admin")
    public ModelAndView admin() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/adminPage.html");

        return modelAndView;
    }

}



