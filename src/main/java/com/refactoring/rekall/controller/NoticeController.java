package com.refactoring.rekall.controller;

import com.refactoring.rekall.dto.CategoryDTO;
import com.refactoring.rekall.dto.NoticeDTO;
import com.refactoring.rekall.service.NoticeService;
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
public class NoticeController {

    @Autowired
    NoticeService noticeService;

//  ------------------------------------- ★ Notice / FAQ ★ ---------------------------------------------------------------
    @GetMapping("notice") // 공지사항
    public ModelAndView noticeList(@SessionAttribute(name ="loginId", required = false) String loginId,
                                   @SessionAttribute(name ="userRole", required = false) String userRole) {
        ModelAndView modelAndView = new ModelAndView();
        List<NoticeDTO> noticeDTOList = noticeService.findList("notice");

        modelAndView.addObject("loginId", loginId);
        modelAndView.addObject("userRole", userRole);
        modelAndView.addObject("noticeList", noticeDTOList);
        modelAndView.setViewName("pages/community/notice.html");
        return modelAndView;
    }

//  ★ FAQ ★ ---------------------------------------------------------------
    @GetMapping("qna") // 자주 묻는 질문
    public ModelAndView qna(@SessionAttribute(name ="loginId", required = false) String loginId,
                            @SessionAttribute(name ="userRole", required = false) String userRole,
                            @RequestParam(value="sort", defaultValue = "all") String categoryId) {

        ModelAndView modelAndView = new ModelAndView();

        List<NoticeDTO> noticeDTOList = noticeService.findList(categoryId);
        List<CategoryDTO> category = noticeService.findCategory();

        modelAndView.addObject("loginId", loginId);
        modelAndView.addObject("userRole", userRole);
        modelAndView.addObject("categoryId", categoryId);
        modelAndView.addObject("category", category);
        modelAndView.addObject("qnaList", noticeDTOList);
        modelAndView.setViewName("pages/community/qna.html");
        return modelAndView;
    }

}
