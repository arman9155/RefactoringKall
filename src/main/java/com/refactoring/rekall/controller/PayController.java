package com.refactoring.rekall.controller;

import com.google.gson.JsonObject;
import com.refactoring.rekall.dto.*;
import com.refactoring.rekall.service.OrderService;
import com.refactoring.rekall.service.PayService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@AllArgsConstructor
public class PayController {

    private final PayService payService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderController orderController;


    //결제요청
    @ResponseBody
    @PostMapping("kakao/ready")
    public KakaoReadyDTO readyToKakaoPay(@RequestParam("loginId") String loginId,
                                         @RequestParam("product")String product,
                                         @RequestParam("quantity")String quantity,
                                         @RequestParam("totalP")String totalP,
                                         @RequestParam("orderId") String orderId,
                                         @RequestParam("productId")String productId) {

        return payService.kakaoPayReady(loginId, product, quantity, totalP, orderId, productId);
    }
    // 결제 성공
    @GetMapping("kakao/success")
    public ModelAndView afterPayRequest(@RequestParam("pg_token") String pgToken,
                                        HttpSession session) {

        String orderId = session.getAttribute("orderId").toString();
        String loginId = session.getAttribute("loginId").toString();
        KakaoApproveDTO kakaoApprove = payService.approveResponse(pgToken, orderId, loginId);
        ResponseEntity<KakaoApproveDTO> kakaoApproveDTO = new ResponseEntity<>(kakaoApprove, HttpStatus.OK);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("data", new Message("", "/order/complete?orderId="+orderId+"&loginId="+loginId));
        modelAndView.setViewName("/common/payComplete.html");

        return modelAndView;
    }

//    //결제 진행 중 취소
//    @GetMapping("/cancel")
//    public void cancel() {
//
//        throw new BusinessLogicException(ExceptionCode.PAY_CANCEL);
//    }
//
//    //결제 실패
//    @GetMapping("/fail")
//    public void fail() {
//
//        throw new BusinessLogicException(ExceptionCode.PAY_FAILED);
//    }
}


