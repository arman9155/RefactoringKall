package com.refactoring.rekall.controller;

import com.refactoring.rekall.dto.RefundDTO;
import com.refactoring.rekall.service.RefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.UsesSunMisc;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class RefundController {
    @Autowired
    RefundService refundService;

//  ------------------------------------- ★ mypage refund List ★ ------------------------------------------------------------

    @GetMapping("mypage/refund")
    public ModelAndView refundIdList (HttpSession session,
                                      @RequestParam(name = "sort", defaultValue = "all", required = false) String status){

        ModelAndView modelAndView = new ModelAndView();
        List<RefundDTO> refundDTOList = refundService.getRefundList(session.getAttribute("loginId").toString(), status);


        modelAndView.addObject("status", status);
        modelAndView.addObject("refundList",refundDTOList);
        modelAndView.setViewName("pages/mypage/order/refundList.html");

        return modelAndView;
    }


//  ------------------------------------- ★ 관리자 ★ ------------------------------------------------------------
//  ------------------------------------- ★ 전체 refund List ★ ------------------------------------------------------------

    @GetMapping("admin/refund")
    public ModelAndView refundList (@RequestParam(name = "sort", defaultValue = "all", required = false) String status){

        ModelAndView modelAndView = new ModelAndView();
        List<RefundDTO> refundDTOList = refundService.getAllRefund(status);

        modelAndView.addObject("status", status);
        modelAndView.addObject("refundList",refundDTOList);
        modelAndView.setViewName("admin/refund/refundList.html");

        return modelAndView;
    }


}
