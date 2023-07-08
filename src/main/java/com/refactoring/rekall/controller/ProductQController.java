package com.refactoring.rekall.controller;

import com.refactoring.rekall.dto.Message;
import com.refactoring.rekall.dto.ProductDTO;
import com.refactoring.rekall.dto.ProductQDTO;
import com.refactoring.rekall.entity.ProductEntity;
import com.refactoring.rekall.entity.ProductQEntity;
import com.refactoring.rekall.service.ProductQService;
import com.refactoring.rekall.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;


@Controller
@AllArgsConstructor
public class ProductQController {
    @Autowired
    ProductQService productQService;
    @Autowired
    ProductService productService;

//  ------------------------------------- ★ 상품 문의 저장 ★ ------------------------------------------------------------
    @PostMapping("productQ/{id}")
    public ModelAndView productQSave(@SessionAttribute(name ="loginId", required = false) String loginId,
                                     @SessionAttribute(name ="userRole", required = false) String userRole,
                                     @ModelAttribute("productQDTO") ProductQDTO productQDTO,
                                     @PathVariable("id") Integer productId) {
        productQService.saveProductQ(productQDTO, productId, loginId);

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("loginId", loginId);  //세션
        modelAndView.addObject("userRole", userRole);  //세션
        modelAndView.addObject("data", new Message("완료되었습니다.", "/product/"+productId));
        modelAndView.setViewName("common/message.html");
/*        modelAndView.setViewName(String.format("product/%d", productId));*/

        return modelAndView;
    }

//  ------------------------------------- ★ 상품 문의 리스트 ★ ------------------------------------------------------------
    @GetMapping("productQList")
    public ModelAndView productQList(@SessionAttribute(name ="loginId", required = false) String loginId,
                                     @RequestParam("page") String page ) {

        ModelAndView modelAndView = new ModelAndView();
        List<ProductQDTO> productQList = new ArrayList<>();

        if(page.equals("mypage")) {
            productQList = productQService.findList(loginId);
            modelAndView.setViewName("pages/mypage/board/productQList.html");
        } else {
            productQList = productQService.findAll();
            modelAndView.setViewName("admin/productQ/productQList.html");
        }
        modelAndView.addObject("productQList", productQList);

        return modelAndView;
    }


//  ---------------------------- ★  상품 문의 삭제 ★ ---------------------------------------------------------------
    @PostMapping("productQ_Del")
    public ModelAndView deleteUsQ(@SessionAttribute(name ="loginId", required = false) String loginId,
                          @RequestParam(name="productqIds", required = false, defaultValue = "") List<Integer> ids,
                          @RequestParam(name="productqId", required = false, defaultValue = "") Integer id,
                          @RequestParam(name="page", defaultValue = "mypage") String page) {

        ModelAndView modelAndView = new ModelAndView();
        if(page.equals("detail")) {
            productQService.productQIdDel(id);
            modelAndView.addObject("data", new Message("삭제되었습니다.", "close"));
        } else {
            productQService.productQDel(ids);
            modelAndView.addObject("data", new Message("삭제되었습니다.", "/productQList?page="+page));
        }
        modelAndView.setViewName("common/message.html");
        return modelAndView;

    }

//  ---------------------------- ★  관리자 ★ ---------------------------------------------------------------

//  ---------------------------- ★  상품 문의 상세 ★ ---------------------------------------------------------------
    @GetMapping("productQ_Detail")
    public ModelAndView productQDetail(@RequestParam("productqId") Integer qId) {

        ModelAndView modelAndView = new ModelAndView();

        ProductQDTO productQ = productQService.findId(qId);
        System.out.println(qId);
        System.out.println(productQ.getProductqId());
        modelAndView.addObject("productQ", productQ);
        modelAndView.setViewName("admin/productQ/productQDetail.html");

        return modelAndView;
    }

   @PostMapping("productQ_comment")
   public ModelAndView productQDetail(@ModelAttribute("productQ") ProductQDTO productQDTO) {
        ModelAndView modelAndView = new ModelAndView();

        productQService.saveDTO(productQDTO);

        modelAndView.addObject("data",new Message("답변이 등록되었습니다.","productQ_Detail?productqId="+productQDTO.getProductqId()));
         modelAndView.setViewName("common/message.html");

         return modelAndView;

   }

}
