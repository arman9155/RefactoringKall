package com.refactoring.rekall.controller;

import com.refactoring.rekall.dto.NoticeDTO;
import com.refactoring.rekall.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class NoticeController {

    @Autowired
    NoticeService noticeService;

//  ------------------------------------- ★ Notice / FAQ★ ---------------------------------------------------------------
    @GetMapping("notice") // 공지사항
    public ModelAndView noticeList() {
        ModelAndView modelAndView = new ModelAndView();
        List<NoticeDTO> noticeDTOList = noticeService.findList("notice");
        modelAndView.addObject("noticeList", noticeDTOList);
        modelAndView.setViewName("pages/community/notice.html");
        return modelAndView;
    }

    @GetMapping("qna") // 자주 묻는 질문
    public ModelAndView qna() {
        ModelAndView modelAndView = new ModelAndView();
        List<NoticeDTO> noticeDTOList = noticeService.findList("q");
        modelAndView.addObject("qnaList", noticeDTOList);
        modelAndView.setViewName("pages/community/qna.html");
        return modelAndView;
    }
}
