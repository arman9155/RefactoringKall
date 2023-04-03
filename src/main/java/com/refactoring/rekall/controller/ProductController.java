package com.refactoring.rekall.controller;

import com.refactoring.rekall.dto.*;
import com.refactoring.rekall.service.CategoryService;
import com.refactoring.rekall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

//  ------------------------------------- ★ Product List ★ ------------------------------------------------------------
    @GetMapping("customList") // 상품리스트 _ custom
    public ModelAndView customList(@SessionAttribute(name ="loginId", required = false) String loginId,
                                   @SessionAttribute(name ="userRole", required = false) String userRole) {
        ModelAndView modelAndView = new ModelAndView();
        List<ProductDTO> productDTOList = productService.findById("custom");
        String value = categoryService.findByCategoryId("custom");

        modelAndView.addObject("productList", productDTOList);
        modelAndView.addObject("category", value);
        modelAndView.addObject("loginId", loginId);
        modelAndView.addObject("userRole", userRole);
        modelAndView.setViewName("pages/product/productList.html");

        return modelAndView;
    }

    @GetMapping("designList") // 상품리스트 _ desingn
    public ModelAndView designList(@SessionAttribute(name ="loginId", required = false) String loginId,
                                   @SessionAttribute(name ="userRole", required = false) String userRole) {
        ModelAndView modelAndView = new ModelAndView();
        List<ProductDTO> productDTOList = productService.findById("design");
        String value = categoryService.findByCategoryId("design");


        modelAndView.addObject("productList", productDTOList);
        modelAndView.addObject("category", value);
        modelAndView.addObject("loginId", loginId);
        modelAndView.addObject("userRole", userRole);
        modelAndView.setViewName("pages/product/productList.html");

        return modelAndView;
    }

    @GetMapping("etcList") // 상품리스트 _ etc
    public ModelAndView etcList(@SessionAttribute(name ="loginId", required = false) String loginId,
                                @SessionAttribute(name ="userRole", required = false) String userRole) {
        ModelAndView modelAndView = new ModelAndView();
        List<ProductDTO> productDTOList = productService.findById("etc");
        String value = categoryService.findByCategoryId("etc");

        modelAndView.addObject("productList", productDTOList);
        modelAndView.addObject("category", value);
        modelAndView.addObject("loginId", loginId);
        modelAndView.addObject("userRole", userRole);
        modelAndView.setViewName("pages/product/productList.html");

        return modelAndView;
    }

//  ------------------------------------- ★ 상세페이지 연결 ★ ------------------------------------------------------------
    @GetMapping("product/{id}") // 상품 상세페이지
        public ModelAndView product(@SessionAttribute(name ="loginId", required = false) String loginId,
                                    @SessionAttribute(name ="userRole", required = false) String userRole,
                                    @PathVariable("id") Integer productId) {
        ModelAndView modelAndView = new ModelAndView();
        ProductDTO productDTO = productService.findByProductId(productId); // 상품정보
        List<ProductImgDTO> productImgDTO = productService.productImgList(productId); //상세사진
        List<ReviewDTO> reviewDTOList = productService.findReview(productId); // 리뷰리스트
        List<ProductQDTO> productQList = productService.findproductQ(productId); // 문의리스트
        /*List< ReviewCmtDTO> reviewCmtDTOList = productService.reviewCmt()*/

        String[] tags = productService.findtags(productDTO); // 제목나오게
        String value = productDTO.getName(); //태그 넣기

        modelAndView.addObject("loginId", loginId);  //세션
        modelAndView.addObject("userRole", userRole);  //세션
        modelAndView.addObject("productName", value);  // 상품명
        modelAndView.addObject("productDTO", productDTO); // 상품정보
        modelAndView.addObject("detailImg", productImgDTO); // 상품 디테일 사진
        modelAndView.addObject("review", reviewDTOList); // 리뷰 리스트 보내기
        modelAndView.addObject("tags", tags);
        modelAndView.setViewName("pages/product/customDetail.html");

        return modelAndView;
    }
}
