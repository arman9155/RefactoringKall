package com.refactoring.rekall.controller;

import com.refactoring.rekall.dto.ProductDTO;
import com.refactoring.rekall.dto.WishListDTO;
import com.refactoring.rekall.service.ProductService;
import com.refactoring.rekall.service.WishListService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@AllArgsConstructor
public class WishListController {

    @Autowired
    WishListService wishListService;

//  ------------------------------------- ★ wishList 저장★ ------------------------------------------------------------

    @GetMapping("wishList/{wish}")
    public void getWishList(@PathVariable("wish") ProductDTO productDTO,
                            @SessionAttribute(name ="loginId", required = false) String loginId) {
        wishListService.getWishList(productDTO, loginId);
    }

}
