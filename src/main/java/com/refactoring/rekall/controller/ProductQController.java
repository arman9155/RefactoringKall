package com.refactoring.rekall.controller;

import com.refactoring.rekall.dto.ProductQDTO;
import com.refactoring.rekall.service.ProductQService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@AllArgsConstructor
public class ProductQController {
    @Autowired
    ProductQService productQService;

//  ------------------------------------- ★ 상품 문의 저장 ★ ------------------------------------------------------------
    @PostMapping("product_q/{id}")
    public ModelAndView productQSave(@SessionAttribute(name ="loginId", required = false) String loginId,
                                     @SessionAttribute(name ="userRole", required = false) String userRole,
                                     @ModelAttribute("productQDTO") ProductQDTO productQDTO,
                                     @PathVariable("id") Integer productId) {
        productQService.saveProductQ(productQDTO, productId, loginId);

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("loginId", loginId);  //세션
        modelAndView.addObject("userRole", userRole);  //세션

        modelAndView.setViewName("product/"+productId);
/*        modelAndView.setViewName(String.format("product/%d", productId));*/

        return modelAndView;
    }


}
