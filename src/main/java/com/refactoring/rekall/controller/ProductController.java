package com.refactoring.rekall.controller;

import com.refactoring.rekall.dto.ProductDTO;
import com.refactoring.rekall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;

//  ------------------------------------- ★ Product List ★ ------------------------------------------------------------

    @GetMapping("customList") // 상품리스트 _ custom
    public ModelAndView customList() {
        ModelAndView modelAndView = new ModelAndView();
        List<ProductDTO> productDTOList = productService.findById("custom");
        modelAndView.addObject("productList", productDTOList);
        modelAndView.setViewName("pages/product/customList.html");

        return modelAndView;
    }

    @GetMapping("designList") // 상품리스트 _ desingn
    public ModelAndView designList() {
        ModelAndView modelAndView = new ModelAndView();
        List<ProductDTO> productDTOList = productService.findById("design");
        modelAndView.addObject("productList", productDTOList);
        modelAndView.setViewName("pages/product/designList.html");

        return modelAndView;
    }

    @GetMapping("etcList") // 상품리스트 _ etc
    public ModelAndView etcList() {
        ModelAndView modelAndView = new ModelAndView();
        List<ProductDTO> productDTOList = productService.findById("etc");
        modelAndView.addObject("productList", productDTOList);
        modelAndView.setViewName("pages/product/etcList.html");

        return modelAndView;
    }

//  ------------------------------------- ★ 상세페이지 연결 ★ ------------------------------------------------------------
    @GetMapping("product") // 상품 상세페이지
        public ModelAndView product() {
        ModelAndView modelAndView = new ModelAndView();
        List<ProductDTO> productDTOList = productService.findById("etc");
        modelAndView.addObject("productList", productDTOList);
        modelAndView.setViewName("pages/product/etcList.html");

        return modelAndView;
}
}
