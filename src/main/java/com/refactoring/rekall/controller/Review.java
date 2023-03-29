package com.refactoring.rekall.controller;

import com.refactoring.rekall.dto.ProductDTO;
import com.refactoring.rekall.dto.ReviewDTO;
import com.refactoring.rekall.service.ProductService;
import com.refactoring.rekall.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class Review {

    @Autowired
    ReviewService reviewService;

    //  ------------------------------------- ★ Community ★ ---------------------------------------------------------------
/*
    @GetMapping("review") // 후기
    public ModelAndView review() {
        ModelAndView modelAndView = new ModelAndView();
        List<ReviewDTO> reviewList = reviewService.findReview();

        List<ProductDTO> productList = reviewService.findproduct();

        modelAndView.addObject("productList",  productList );
        modelAndView.addObject("reviewList",  reviewList);
        modelAndView.setViewName("pages/community/review.html");
        return modelAndView;
    }
*/


}
