package com.refactoring.rekall.controller;

import com.refactoring.rekall.dto.OrderDTO;
import com.refactoring.rekall.dto.OrderDetailDTO;
import com.refactoring.rekall.dto.UserDTO;
import com.refactoring.rekall.service.OrderService;
import com.refactoring.rekall.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;

//  ------------------------------------- ★ mypage order List ★ ------------------------------------------------------------
    @GetMapping("u_order")
    public ModelAndView orderList (@SessionAttribute(name ="loginId", required = false) String loginId,
                                   @SessionAttribute(name ="userRole", required = false) String userRole,
                                   @RequestParam(name = "sort", defaultValue = "all", required = false) String status){

        ModelAndView modelAndView = new ModelAndView();
        List<OrderDTO> orderDTOList = orderService.getOrderList(loginId, status);

        modelAndView.addObject("loginId", loginId);
        modelAndView.addObject("userRole", userRole);
        modelAndView.addObject("status", status);
        modelAndView.addObject("odetailList",orderDTOList);
        modelAndView.setViewName("pages/mypage/order/orderList.html");

        return modelAndView;
    }

//  ------------------------------------- ★ mypage mileage List ★ ------------------------------------------------------------
@GetMapping("mileage")
    public ModelAndView mileageList (@SessionAttribute(name ="loginId", required = false) String loginId,
                                   @SessionAttribute(name ="userRole", required = false) String userRole,
                                   @RequestParam(name = "sort", defaultValue = "all", required = false) String status){

        ModelAndView modelAndView = new ModelAndView();
        List<OrderDTO> orderDTOList = orderService.getMileageList(loginId);
        UserDTO userDTO = userService.findByUserID(loginId);

        modelAndView.addObject("loginId", loginId);
        modelAndView.addObject("userRole", userRole);
        modelAndView.addObject("user", userDTO);
        modelAndView.addObject("status", status);
        modelAndView.addObject("orderList",orderDTOList);
        modelAndView.setViewName("pages/mypage/profile/mileage.html");

        return modelAndView;
    }
//  ------------------------------------- ★ 바로 주문 처리 ★ ------------------------------------------------------------

    @PostMapping("order/{loginId}") //  바로 주문 시 odetial 저장 처리
    public ModelAndView order(@PathVariable("loginId") String loginId, String userRole,
                              @RequestParam(value="DTO") String[] dtos,
                              @RequestParam("photos") MultipartFile[] imgFiles) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

/*        odetailDTOList = orderService.file(odetailDTOList, imgFiles, loginId);*/

        modelAndView.addObject("loginId", loginId);
        modelAndView.addObject("userRole", userRole);
//        modelAndView.addObject("odetailList",odetailDTOList);
        modelAndView.setViewName("pages/order/order.html");

        return modelAndView;
    }

//  ------------------------------------- ★ 관리자 페이지★ ------------------------------------------------------------
//  ------------------------------------- ★ 전체 / sort order List ★ ------------------------------------------------------------

    @GetMapping("a_order")
    public ModelAndView allOrderList (@RequestParam(name = "sort", defaultValue = "all", required = false) String status){

        ModelAndView modelAndView = new ModelAndView();
        List<OrderDTO> orderDTOList = orderService.getAllOrderList(status);

        modelAndView.addObject("status", status);
        modelAndView.addObject("odetailList",orderDTOList);
        modelAndView.setViewName("admin/order/orderList.html");

        return modelAndView;
    }
//  ------------------------------------- ★ 주문상세정보 ★ ------------------------------------------------------------
    @GetMapping("orderDetail")
    public ModelAndView orderDetail (@RequestParam("odetailId") Integer odetailId){

        ModelAndView modelAndView = new ModelAndView();
        OrderDetailDTO orderDetailDTO = orderService.getOrderDetail(odetailId);
        OrderDTO orderDTO = orderService.getOrderDTO(odetailId);

        modelAndView.addObject("orderDTO", orderDTO);
        modelAndView.addObject("odetail",orderDetailDTO);
        modelAndView.setViewName("admin/order/orderDetail.html");

        return modelAndView;
    }

}
