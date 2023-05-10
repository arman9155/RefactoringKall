package com.refactoring.rekall.controller;

import com.refactoring.rekall.dto.Message;
import com.refactoring.rekall.dto.ProductDTO;
import com.refactoring.rekall.dto.WishListDTO;
import com.refactoring.rekall.service.ProductService;
import com.refactoring.rekall.service.WishListService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
public class WishListController {

    @Autowired
    WishListService wishListService;

//  ------------------------------------- ★ 로그인 id 별 wishList 뽑기★ ------------------------------------------------------------
    @GetMapping("u_wishList")
    public ModelAndView wishList(@SessionAttribute(name ="loginId", required = false) String loginId,
                                 @SessionAttribute(name ="userRole", required = false) String userRole) {
        ModelAndView modelAndView = new ModelAndView();

        List<WishListDTO> wishList = wishListService.findWishList(loginId);

        modelAndView.addObject("loginId", loginId);
        modelAndView.addObject("userRole", userRole);
        modelAndView.addObject("wishList", wishList);

        modelAndView.setViewName("pages/mypage/wishList.html");

        return modelAndView;
    }

//  ------------------------------------- ★ wishList 저장★ ------------------------------------------------------------
    @GetMapping("u_wishList/{wish}")
    public String getWishList(@PathVariable("wish") Integer productId,
                              @RequestParam(name ="loginId", required = false) String loginId,
                              @RequestParam(name ="url", required = false) String url,
                              @RequestParam(name ="userRole", required = false) String userRole) {

        wishListService.getWishList(productId, loginId);
        return "redirect:/"+url;
    }

    @GetMapping("u_wishListDel/{wish}")
    public String delWishList(@PathVariable("wish") Integer productId,
                              @RequestParam(name ="loginId", required = false) String loginId,
                              @RequestParam(name ="url", required = false) String url,
                              @RequestParam(name ="userRole", required = false) String userRole) {

        wishListService.delWishList(productId, loginId);
        return "redirect:/"+url;
    }
}
