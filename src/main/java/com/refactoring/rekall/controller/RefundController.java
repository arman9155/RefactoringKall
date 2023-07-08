package com.refactoring.rekall.controller;

import com.refactoring.rekall.dto.RefundDTO;
import com.refactoring.rekall.service.RefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RefundController {
    @Autowired
    RefundService refundService;

//  ------------------------------------- ★ mypage refund List ★ ------------------------------------------------------------

    @GetMapping("u_refund")
    public ModelAndView refundIdList (@SessionAttribute(name ="loginId", required = false) String loginId,
                                   @SessionAttribute(name ="userRole", required = false) String userRole,
                                   @RequestParam(name = "sort", defaultValue = "all", required = false) String status){

        ModelAndView modelAndView = new ModelAndView();
        List<RefundDTO> refundDTOList = refundService.getRefundList(loginId, status);

        modelAndView.addObject("loginId", loginId);
        modelAndView.addObject("userRole", userRole);
        modelAndView.addObject("status", status);
        modelAndView.addObject("refundList",refundDTOList);
        modelAndView.setViewName("pages/mypage/order/refundList.html");

        return modelAndView;
    }




//  ------------------------------------- ★ 관리자 ★ ------------------------------------------------------------
//  ------------------------------------- ★ 전체 refund List ★ ------------------------------------------------------------

    @GetMapping("a_refund")
    public ModelAndView refundList (@RequestParam(name = "sort", defaultValue = "all", required = false) String status){

        ModelAndView modelAndView = new ModelAndView();
        List<RefundDTO> refundDTOList = refundService.getAllRefund(status);

        modelAndView.addObject("status", status);
        modelAndView.addObject("refundList",refundDTOList);
        modelAndView.setViewName("admin/refund/refundList.html");

        return modelAndView;
    }


}
