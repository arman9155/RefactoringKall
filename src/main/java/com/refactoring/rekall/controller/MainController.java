package com.refactoring.rekall.controller;

import com.refactoring.rekall.Auth;
import com.refactoring.rekall.dto.ProductDTO;
import com.refactoring.rekall.dto.ProductImgDTO;
import com.refactoring.rekall.entity.ProductImgEntity;
import com.refactoring.rekall.repository.ProductImgRepository;
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
    @Autowired
    ProductImgRepository productImgRepository;

//  ------------------------------------- ★ Product List ★ ------------------------------------------------------------
    @GetMapping(value = {"", "main"})
    public ModelAndView toMain() {
        ModelAndView modelAndView = new ModelAndView();

        List<ProductDTO> productDTOS = productService.findAll();
        List<ProductDTO> productDTOList = new ArrayList<>(6);
        if(productDTOS.size() >=6)
            productDTOList = productDTOS.subList(0, 6);
        else
            productDTOList = productDTOS;

        List<ProductImgEntity> imgList = productImgRepository.findimg(1);

/*        if("".equals(session.getAttribute("userId"))){

        }*/

        modelAndView.addObject("imgList", imgList);
        modelAndView.addObject("recommendProduct", productDTOList);
        modelAndView.setViewName("pages/main.html");

        return modelAndView;
    }

//  ------------------------------------- ★ Community ★ ---------------------------------------------------------------

    @GetMapping("community/road") // 찾아오시는 길
    public ModelAndView road() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("pages/community/road.html");
        return modelAndView;
    }

//  ------------------------------------- ★ admin 페이지 ★ ---------------------------------------------------------------

    @Auth(role= Auth.Role.ADMIN)
    @GetMapping("admin")
    public ModelAndView admin() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/adminPage.html");

        return modelAndView;
    }
    
    
//  ------------------------------------- ★ 창닫기 ★ ---------------------------------------------------------------

    @GetMapping("close")
    public ModelAndView close(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/common/close.html");
        return modelAndView;
    }
}



