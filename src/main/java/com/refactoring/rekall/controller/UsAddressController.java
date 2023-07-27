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
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class UsAddressController {
    @Autowired
    UsAddressService usAddressService;

//  ------------------------------------- ★ 마이페이지 - 배송지 관리★ ---------------------------------------------------------------
    @GetMapping(value={"/mypage/address", "order/address"})
    public ModelAndView getAddress(HttpSession session,
                                   @RequestParam(name = "page", required = false, defaultValue="mypage")String page) {
        ModelAndView modelAndView = new ModelAndView();
        List<UsAddressDTO> usAddressDTOList = usAddressService.getAddressList(session.getAttribute("loginId").toString());


        if("mypage".equals(page))  modelAndView.setViewName("/pages/mypage/profile/userAddress.html");
        else modelAndView.setViewName("/pages/order/address.html");
        modelAndView.addObject("addressList", usAddressDTOList);
        return modelAndView;
    }

//  ★ 기본 배송지 설정★ ---------------------------------------------------------------
    @PostMapping("/mypage/address/default")
    public ModelAndView  setAddress(@RequestParam("addressId") Integer addressId,
                                   HttpSession session) {
        String loginId = session.getAttribute("loginId").toString();
        usAddressService.findAddressId(addressId, loginId);

        return getAddress(session, "mypage");
    }

//  ------------------------------------- ★ 마이페이지 - 배송지 디테일★ ---------------------------------------------------------------
    @GetMapping("/mypage/address/detail")
    public ModelAndView addressDetail(@RequestParam("addressId") Integer addressId) {
        ModelAndView modelAndView = new ModelAndView();
        UsAddressDTO usAddressDTO = usAddressService.getAddress(addressId);

        modelAndView.addObject("usAddressDTO", usAddressDTO);
        modelAndView.setViewName("pages/mypage/profile/addressDetail.html");
        return modelAndView;
    }

//  ------------------------------------- ★ 마이페이지 - 배송지 수정 페이지★ ---------------------------------------------------------------
    @GetMapping("/mypage/address/change")
    public ModelAndView addressChangeF(@RequestParam("addressId") Integer addressId) {
        ModelAndView modelAndView = new ModelAndView();
        UsAddressDTO usAddressDTO = usAddressService.getAddress(addressId);

        modelAndView.addObject("usAddressDTO", usAddressDTO);
        modelAndView.addObject("addressId", addressId);
        modelAndView.setViewName("pages/mypage/profile/addressChange.html");

        return modelAndView;
    }

//  ★ 마이페이지 - 배송지 수정 완료★ ---------------------------------------------------------------
    @PostMapping("/mypage/address/change")
    public ModelAndView addressChange(@ModelAttribute UsAddressDTO usAddressDTO,
                                      @RequestParam("addressId") Integer addressId,
                                      HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        usAddressDTO.setAddressId(addressId);
        usAddressService.saveAddress(usAddressDTO, session.getAttribute("loginId").toString());

        modelAndView.addObject("data", new Message("수정되었습니다.", "u_address_detail?addressId=" + addressId));
        modelAndView.setViewName("common/message.html");
        return modelAndView;
    }


    //  ------------------------------------- ★ 마이페이지 - 배송지 추가★ ---------------------------------------------------------------
    @GetMapping(value={"/mypage/address/add", "order/address/add"})
    public ModelAndView addressAdd() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("usAddressDTO", new UsAddressDTO());
        modelAndView.setViewName("pages/mypage/profile/addressAdd.html");
        return modelAndView;
    }

    @PostMapping(value={"/mypage/address/add", "order/address/add"})
    public ModelAndView addressAdd(@ModelAttribute UsAddressDTO usAddressDTO,
                                  HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();

        usAddressService.saveAddress(usAddressDTO, session.getAttribute("loginId").toString());

        modelAndView.addObject("data", new Message("추가되었습니다.", "/close"));
        modelAndView.setViewName("common/message.html");
        return modelAndView;
    }

//  ------------------------------------- ★ 마이페이지 - 배송지 삭제★ ---------------------------------------------------------------
    @GetMapping("/mypage/address/del")
    public ModelAndView addressDel(@RequestParam(required = false, name="addressId") Integer addressId,
                                   @RequestParam(required = false, name="popup") Integer addressId2) {
        ModelAndView modelAndView = new ModelAndView();
        System.out.println("addressId"+addressId);
        if(addressId != null) {
            usAddressService.addressDel(addressId);
            modelAndView.addObject("data", new Message("삭제되었습니다.", "/mypage/address"));
        } else if (addressId2 != null) {
            usAddressService.addressDel(addressId2);
            modelAndView.addObject("data", new Message("삭제되었습니다.", "/close"));
        }

        modelAndView.setViewName("common/message.html");

        return modelAndView;
    }
}
