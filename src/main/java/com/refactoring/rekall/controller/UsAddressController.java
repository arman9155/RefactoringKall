package com.refactoring.rekall.controller;

import com.refactoring.rekall.dto.Message;
import com.refactoring.rekall.dto.UsAddressDTO;
import com.refactoring.rekall.service.UsAddressService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class UsAddressController {
    @Autowired
    UsAddressService usAddressService;

//  ------------------------------------- ★ 마이페이지 - 배송지 관리★ ---------------------------------------------------------------
    @GetMapping("u_address")
    public ModelAndView getAddress(@SessionAttribute(name ="loginId", required = false) String loginId,
                                         @SessionAttribute(name ="userRole", required = false) String userRole) {
        ModelAndView modelAndView = new ModelAndView();
        List<UsAddressDTO> usAddressDTOList = usAddressService.getAddressList(loginId);

        modelAndView.addObject("addressList", usAddressDTOList);
        modelAndView.addObject("loginId", loginId);
        modelAndView.addObject("userRole", userRole);
        modelAndView.setViewName("pages/mypage/userAddress.html");
        return modelAndView;
    }

//  ★ 기본 배송지 설정★ ---------------------------------------------------------------
    @PostMapping("u_default_address")
    public ModelAndView setAddress(@RequestParam("addressId") Integer addressId,
                                   @SessionAttribute(name ="loginId", required = false) String loginId,
                                   @SessionAttribute(name ="userRole", required = false) String userRole) {
        usAddressService.findAddressId(addressId, loginId);

        return getAddress(loginId,userRole);
    }

//  ------------------------------------- ★ 마이페이지 - 배송지 디테일★ ---------------------------------------------------------------
    @GetMapping("u_address_detail")
    public ModelAndView addressDetail(@RequestParam("addressId") Integer addressId) {
        ModelAndView modelAndView = new ModelAndView();
        UsAddressDTO usAddressDTO = usAddressService.getAddress(addressId);

        modelAndView.addObject("usAddressDTO", usAddressDTO);
        modelAndView.setViewName("pages/mypage/addressDetail.html");
        return modelAndView;
    }

//  ------------------------------------- ★ 마이페이지 - 배송지 수정 페이지★ ---------------------------------------------------------------
    @GetMapping("u_address_change")
    public ModelAndView addressChangeF(@RequestParam("addressId") Integer addressId) {
        ModelAndView modelAndView = new ModelAndView();
        UsAddressDTO usAddressDTO = usAddressService.getAddress(addressId);

        modelAndView.addObject("usAddressDTO", usAddressDTO);
        modelAndView.setViewName("pages/mypage/addressChange.html");
        return modelAndView;
    }

//  ★ 마이페이지 - 배송지 수정 완료★ ---------------------------------------------------------------
    @PostMapping("u_address_change")
    public ModelAndView addressChange(@ModelAttribute UsAddressDTO usAddressDTO) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("data", new Message("수정되었습니다.", "u_address_detail"));
        modelAndView.addObject("addressId", usAddressDTO.getAddressId());
        modelAndView.setViewName("common/fragments/message.html");
        return modelAndView;
    }

    @GetMapping("close")
    public ModelAndView close() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("close.html");
        return modelAndView;
    }

    //  ------------------------------------- ★ 마이페이지 - 배송지 추가★ ---------------------------------------------------------------
    @GetMapping("u_address_add")
    public ModelAndView addressAdd(@SessionAttribute(name ="loginId", required = false) String loginId) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("usAddressDTO", new UsAddressDTO());
        modelAndView.setViewName("pages/mypage/addressAdd.html");
        return modelAndView;
    }

    @PostMapping("u_address_add")
    public ModelAndView addressAdd(@ModelAttribute UsAddressDTO usAddressDTO,
                                   @SessionAttribute(name ="loginId", required = false) String loginId) {
        ModelAndView modelAndView = new ModelAndView();

        usAddressService.saveAddress(usAddressDTO, loginId);
        modelAndView.addObject("data", new Message("추가되었습니다.", "close"));
        modelAndView.setViewName("common/fragments/message.html");
        return modelAndView;
    }

//  ------------------------------------- ★ 마이페이지 - 배송지 삭제★ ---------------------------------------------------------------
    @GetMapping("u_address_del")
    public ModelAndView addressDel(@RequestParam(required = false, name="addressId") Integer addressId,
                                   @RequestParam(required = false, name="popup") Integer addressId2) {
        ModelAndView modelAndView = new ModelAndView();

        if(addressId != null) {
            usAddressService.addressDel(addressId);
            modelAndView.addObject("data", new Message("삭제되었습니다.", "u_address"));
        } else if (addressId2 != null) {
            usAddressService.addressDel(addressId2);
            modelAndView.addObject("data", new Message("삭제되었습니다.", "close"));
        }

        modelAndView.setViewName("common/fragments/message.html");

        return modelAndView;
    }
}
