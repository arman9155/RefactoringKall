package com.refactoring.rekall.controller;

import com.refactoring.rekall.dto.CategoryDTO;
import com.refactoring.rekall.dto.Message;
import com.refactoring.rekall.dto.RefundDTO;
import com.refactoring.rekall.service.CategoryService;
import com.refactoring.rekall.service.RefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.UsesSunMisc;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class RefundController {
    @Autowired
    RefundService refundService;
    @Autowired
    CategoryService categoryService;

//  ------------------------------------- ★ mypage refund List ★ ------------------------------------------------------------

    @GetMapping("mypage/refund")
    public ModelAndView refundIdList (HttpSession session,
                                      @RequestParam(name = "sort", defaultValue = "all", required = false) String status){

        ModelAndView modelAndView = new ModelAndView();
        List<RefundDTO> refundDTOList = refundService.getRefundList(session.getAttribute("loginId").toString(), status);


        modelAndView.addObject("status", status);
        modelAndView.addObject("refundList", refundDTOList);
        modelAndView.setViewName("pages/mypage/order/refundList.html");

        return modelAndView;
    }

//  ------------------------------------- ★ mypage refund List ★ ------------------------------------------------------------

    @GetMapping("mypage/refund/detail")
    public ModelAndView refundDetail (@RequestParam("refundId") Integer refundId){

        ModelAndView modelAndView = new ModelAndView();
        RefundDTO refundDTO= refundService.findRefundDTO(refundId);

        modelAndView.addObject("refund", refundDTO);
        modelAndView.setViewName("pages/mypage/order/refundDetail.html");

        return modelAndView;
    }

    @GetMapping("mypage/refund/change")
    public ModelAndView refundChangeF (@RequestParam("refundId") Integer refundId){

        ModelAndView modelAndView = new ModelAndView();
        RefundDTO refundDTO= refundService.findRefundDTO(refundId);

        modelAndView.addObject("refund", refundDTO);
        modelAndView.setViewName("pages/mypage/order/refundDetailC.html");

        return modelAndView;
    }
    @PostMapping("mypage/refund/change")
    public ModelAndView refundChange(@ModelAttribute("refund") RefundDTO refundDTO,
                                     @RequestParam("files") MultipartFile[] multipartFiles) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        if(multipartFiles != null) {
            System.out.println("<<<<<");
            refundService.setRefund(refundDTO, multipartFiles, "change");

        } else refundService.saveRefundDTO(refundDTO);

        modelAndView.addObject("data", new Message("수정되었습니다.",  "/mypage/refund/detail?refundId="+refundDTO.getRefundId()));
        modelAndView.setViewName("common/message.html");

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
    @GetMapping("admin/refund/detail")
    public ModelAndView refundList (@RequestParam("refundId") Integer refundId){

        ModelAndView modelAndView = new ModelAndView();
        RefundDTO refundDTO = refundService.findRefundDTO(refundId);

        modelAndView.addObject("refund",refundDTO);
        modelAndView.setViewName("admin/refund/refundDetail.html");

        return modelAndView;
    }
    @GetMapping("admin/refund/status")
    public ModelAndView refundStatusF (@RequestParam("refundIds") List<Integer> refundIds){

        ModelAndView modelAndView = new ModelAndView();
        List<CategoryDTO> category = categoryService.findCategory("rf");

        modelAndView.addObject("refundIds", refundIds);
        modelAndView.addObject("category", category);
        modelAndView.setViewName("admin/refund/refundStatus.html");

        return modelAndView;
    }

    @PostMapping("admin/refund/status")
    public ModelAndView refundStatus(@RequestParam("status") String status,
                                      @RequestParam("refundIds") String[] refundIds,
                                         HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();

        refundService.changeStatus(status, refundIds);

        modelAndView.addObject("data", new Message("변경되었습니다.","/close" ));
        modelAndView.setViewName("/common/message.html");
        return modelAndView;
    }

}
