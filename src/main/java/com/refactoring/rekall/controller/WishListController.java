package com.refactoring.rekall.controller;


import com.refactoring.rekall.dto.WishListDTO;
import com.refactoring.rekall.service.WishListService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@AllArgsConstructor
public class WishListController {

    @Autowired
    WishListService wishListService;

//  ------------------------------------- ★ 로그인 id 별 wishList 뽑기★ ------------------------------------------------------------
    @GetMapping("mypage/wish/list")
    public ModelAndView wishList(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();

        List<WishListDTO> wishList = wishListService.findWishList(session.getAttribute("loginId").toString());

        modelAndView.addObject("wishList", wishList);
        modelAndView.setViewName("pages/mypage/wishList.html");

        return modelAndView;
    }

//  ------------------------------------- ★ wishList 저장★ ------------------------------------------------------------
    @GetMapping("wish/{id}")
    @ResponseBody
    public void getWishList(@PathVariable("id") Integer productId, HttpSession session,
                              @RequestParam(name ="url", required = false) String url,
                              @RequestParam(name ="userRole", required = false) String userRole) {

        wishListService.getWishList(productId, session.getAttribute("loginId").toString());
    }

    @GetMapping("wish/del/{id}")
    @ResponseBody
    public void delWishList(@PathVariable("id") Integer productId,   HttpSession session,
                              @RequestParam(name ="url", required = false) String url,
                              @RequestParam(name ="userRole", required = false) String userRole) {

        wishListService.delWishList(productId, session.getAttribute("loginId").toString());
    }
}
