package com.refactoring.rekall.controller;

import com.refactoring.rekall.dto.CategoryDTO;
import com.refactoring.rekall.dto.NoticeDTO;
import com.refactoring.rekall.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

        modelAndView.addObject("noticeList", noticeDTOList);
        modelAndView.addObject("loginId", loginId);
        modelAndView.addObject("userRole", userRole);
        modelAndView.setViewName("pages/community/notice.html");
        return modelAndView;
    }

//  ★ FAQ ★ ---------------------------------------------------------------
    @GetMapping("qna") // 자주 묻는 질문
    public ModelAndView qna(@SessionAttribute(name ="loginId", required = false) String loginId,
                            @SessionAttribute(name ="userRole", required = false) String userRole) {
        ModelAndView modelAndView = new ModelAndView();
        List<NoticeDTO> noticeDTOList = noticeService.findList("q");
        List<CategoryDTO> category = noticeService.findCategory();

        modelAndView.addObject("qnaList", noticeDTOList);
        modelAndView.addObject("category", category);
        modelAndView.addObject("loginId", loginId);
        modelAndView.addObject("userRole", userRole);
        modelAndView.setViewName("pages/community/qna.html");
        return modelAndView;
    }

//  ------------------------------------- ★ FAQ 검색★ ---------------------------------------------------------------
    @GetMapping("faq_01") // QnA 선택 정렬
    public ModelAndView faq_01(@SessionAttribute(name ="loginId", required = false) String loginId,
                               @SessionAttribute(name ="userRole", required = false) String userRole) {
        ModelAndView modelAndView = new ModelAndView();
        List<NoticeDTO> noticeDTOList = noticeService.findList("faq_01");
        List<CategoryDTO> category = noticeService.findCategory();
        System.out.println(noticeDTOList);

        modelAndView.addObject("qnaList", noticeDTOList);
        modelAndView.addObject("category", category);
        modelAndView.addObject("loginId", loginId);
        modelAndView.addObject("userRole", userRole);
        modelAndView.setViewName("pages/community/qna.html");
        return modelAndView;
    }
    @GetMapping("faq_02") // QnA 선택 정렬
    public ModelAndView faq_02(@SessionAttribute(name ="loginId", required = false) String loginId,
                               @SessionAttribute(name ="userRole", required = false) String userRole) {
        ModelAndView modelAndView = new ModelAndView();
        List<NoticeDTO> noticeDTOList = noticeService.findList("faq_02");
        List<CategoryDTO> category = noticeService.findCategory();

        modelAndView.addObject("qnaList", noticeDTOList);
        modelAndView.addObject("category", category);
        modelAndView.addObject("loginId", loginId);
        modelAndView.addObject("userRole", userRole);
        modelAndView.setViewName("pages/community/qna.html");
        return modelAndView;
    }
    @GetMapping("faq_03") // QnA 선택 정렬
    public ModelAndView faq_03(@SessionAttribute(name ="loginId", required = false) String loginId,
                               @SessionAttribute(name ="userRole", required = false) String userRole) {
        ModelAndView modelAndView = new ModelAndView();
        List<NoticeDTO> noticeDTOList = noticeService.findList("faq_03");
        List<CategoryDTO> category = noticeService.findCategory();

        modelAndView.addObject("qnaList", noticeDTOList);
        modelAndView.addObject("category", category);
        modelAndView.addObject("loginId", loginId);
        modelAndView.addObject("userRole", userRole);
        modelAndView.setViewName("pages/community/qna.html");
        return modelAndView;
    }
    @GetMapping("faq_04") // QnA 선택 정렬
    public ModelAndView faq_04(@SessionAttribute(name ="loginId", required = false) String loginId,
                               @SessionAttribute(name ="userRole", required = false) String userRole) {
        ModelAndView modelAndView = new ModelAndView();
        List<NoticeDTO> noticeDTOList = noticeService.findList("faq_04");
        List<CategoryDTO> category = noticeService.findCategory();

        modelAndView.addObject("qnaList", noticeDTOList);
        modelAndView.addObject("category", category);
        modelAndView.addObject("loginId", loginId);
        modelAndView.addObject("userRole", userRole);
        modelAndView.setViewName("pages/community/qna.html");
        return modelAndView;
    }
}
