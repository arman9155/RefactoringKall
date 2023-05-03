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
/*    @GetMapping("u_wishList/{wish}")
    public void getWishList(@PathVariable("wish") Integer productId,
                            @RequestParam(name = "url", required = false) String url,
                             @SessionAttribute(name ="loginId", required = false) String loginId) {

        System.out.println("productId"+productId);
        System.out.println("url"+url);
        wishListService.getWishList(productId, loginId);
        ModelAndView model = new ModelAndView();
        model.addObject("data", new Message("저장되었습니다.", url));
        model.addObject("url",url);
        model.setViewName("common/fragments/message.html");
        // 이거 자바로 alert만 뜨게 수정할 방법 찾기
    }*/
    //alert으로 jquery로 해결해서 message 제외한
    @GetMapping("u_wishList/{wish}")
    public void getWishList(@PathVariable("wish") Integer productId,
                        @SessionAttribute(name ="loginId", required = false) String loginId) {

    System.out.println("productId"+productId);
    wishListService.getWishList(productId, loginId);
    }
/*    @GetMapping("u_wishListDel/{wish}")
    public void delWishList(@PathVariable("wish") Integer productId,
                            @RequestParam(name = "url", required = false) String url,
                            @SessionAttribute(name ="loginId", required = false) String loginId) {
        wishListService.delWishList(productId, loginId);
        ModelAndView model = new ModelAndView();
        model.addObject("data", new Message("삭제되었습니다.", url));
        model.setViewName("common/fragments/message.html");
        // 이거 자바로 alert만 뜨게 수정할 방법 찾기
    }*/
    @GetMapping("u_wishListDel/{wish}")
    public void delWishList(@PathVariable("wish") Integer productId,
                            @SessionAttribute(name ="loginId", required = false) String loginId) {
        wishListService.delWishList(productId, loginId);
    }
}
