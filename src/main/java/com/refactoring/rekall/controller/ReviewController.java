package com.refactoring.rekall.controller;

import com.refactoring.rekall.dto.ReviewDTO;
import com.refactoring.rekall.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    //  ------------------------------------- ★ mypage review ★ ---------------------------------------------------------------

    @GetMapping("u_review") // 후기
    public ModelAndView review(@SessionAttribute(name ="loginId", required = false) String loginId,
                               @SessionAttribute(name ="userRole", required = false) String userRole) {
        ModelAndView modelAndView = new ModelAndView();
        List<ReviewDTO> reviewList = reviewService.findReviewById(loginId);

        modelAndView.addObject("loginId", loginId);
        modelAndView.addObject("userRole", userRole);
        modelAndView.addObject("reviewList",  reviewList);
        modelAndView.setViewName("pages/mypage/board/reviewList.html");

        return modelAndView;
    }



}
