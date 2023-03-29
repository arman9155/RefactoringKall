package com.refactoring.rekall.controller;

import com.refactoring.rekall.dto.UsQDTO;
import com.refactoring.rekall.entity.UsQEntity;
import com.refactoring.rekall.service.UsQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UsQController {

    @Autowired
    UsQService usQService;

//  ---------------------------- ★ 1:1 문의 정렬 ★ ---------------------------------------------------------------
    @GetMapping("question") // 1:1 문의
    public ModelAndView questionF() {

        ModelAndView modelAndView = new ModelAndView();
        UsQDTO usQDTO = new UsQDTO();
        modelAndView.addObject("usq", new UsQDTO());
        modelAndView.setViewName("pages/community/question.html");

        return modelAndView;
    }

    @PostMapping("question") // 1:1 문의
    public ModelAndView question(@ModelAttribute("usQ") UsQDTO usQDTO) {
        usQService.sendQuestion(usQDTO);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/community/complete.html");

        return modelAndView;
    }

}
