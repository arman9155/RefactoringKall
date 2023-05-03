package com.refactoring.rekall.controller;

import com.refactoring.rekall.dto.Message;
import com.refactoring.rekall.dto.UsQDTO;
import com.refactoring.rekall.service.UsQService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
public class UsQController {

    @Autowired
    UsQService usQService;

//  ---------------------------- ★ 1:1 문의 정렬 ★ ---------------------------------------------------------------
    @GetMapping("question") // 1:1 문의
    public ModelAndView questionF(@SessionAttribute(name ="loginId", required = false) String loginId,
                                  @SessionAttribute(name ="userRole", required = false) String userRole) {

        ModelAndView modelAndView = new ModelAndView();
        String email = usQService.findEmail(loginId);
        System.out.println("email"+email);
        System.out.println("loginId"+loginId);

        modelAndView.addObject("usq", new UsQDTO());
        modelAndView.addObject("email", email);
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
        modelAndView.setViewName("common/fragments/message.html");

        return modelAndView;
    }

//  ---------------------------- ★ userID_질문 리스트 ★ ---------------------------------------------------------------
    @GetMapping("u_question") // 1:1 문의
    public ModelAndView u_questionList(@SessionAttribute(name ="loginId", required = false) String loginId,
                                        @SessionAttribute(name ="userRole", required = false) String userRole) {
        ModelAndView modelAndView = new ModelAndView();

        List<UsQDTO> usQList = usQService.usqList(loginId);

        modelAndView.addObject("loginId", loginId);
        modelAndView.addObject("userRole", userRole);
        modelAndView.addObject("usQList", usQList);
        modelAndView.setViewName("pages/mypage/questionList.html");

        return modelAndView;
    }

//  ---------------------------- ★ userID_질문 삭제 ★ ---------------------------------------------------------------
    @PostMapping("u_deleteUsQ")
    public ModelAndView deleteUsQ(@RequestParam("usqIds") List<Integer> usQIds) {
        usQService.deleteUsQ(usQIds);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("data",new Message("삭제되었습니다.", "u_question"));
        modelAndView.setViewName("common/fragments/message.html");

        return modelAndView;
    }


}
