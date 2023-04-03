package com.refactoring.rekall.controller;

import com.refactoring.rekall.dto.Message;
import com.refactoring.rekall.dto.UsQDTO;
import com.refactoring.rekall.entity.UsQEntity;
import com.refactoring.rekall.service.UsQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UsQController {

    @Autowired
    UsQService usQService;

//  ---------------------------- ★ 1:1 문의 정렬 ★ ---------------------------------------------------------------
    @GetMapping("question") // 1:1 문의
    public ModelAndView questionF(@SessionAttribute(name ="loginId", required = false) String loginId,
                                  @SessionAttribute(name ="userRole", required = false) String userRole) {

        ModelAndView modelAndView = new ModelAndView();
        UsQDTO usQDTO = new UsQDTO();

        modelAndView.addObject("usq", new UsQDTO());
        modelAndView.addObject("loginId", loginId);
        modelAndView.addObject("userRole", userRole);
        modelAndView.setViewName("pages/community/question.html");

        return modelAndView;
    }

//  ★ 1:1 문의 완료  ★ ---------------------------------------------------------------
    @PostMapping("question") // 1:1 문의
    public ModelAndView question(@ModelAttribute("usQ") UsQDTO usQDTO) {
        usQService.sendQuestion(usQDTO);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("data",new Message("완료되었습니다.", "notice"));
        modelAndView.setViewName("pages/message.html");

        return modelAndView;
    }

}
