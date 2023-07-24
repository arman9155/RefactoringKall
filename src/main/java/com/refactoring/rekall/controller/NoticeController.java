package com.refactoring.rekall.controller;

import com.refactoring.rekall.dto.CategoryDTO;
import com.refactoring.rekall.dto.Message;
import com.refactoring.rekall.dto.NoticeDTO;
import com.refactoring.rekall.service.CategoryService;
import com.refactoring.rekall.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class NoticeController {

    @Autowired
    NoticeService noticeService;
    @Autowired
    CategoryService categoryService;

//  ------------------------------------- ★ Notice / FAQ ★ ---------------------------------------------------------------
    @GetMapping(value = {"community/notice", "admin/notice"})
    public ModelAndView noticeList(@RequestParam("sort") String categoryId,
                                   @RequestParam(name="page", defaultValue = "user", required = false) String page) {

        ModelAndView modelAndView = new ModelAndView();
        List<NoticeDTO> noticeDTOList = noticeService.findList(categoryId);

        if(categoryId.equals("notice")) {
            modelAndView.setViewName("pages/community/notice.html");
        } else {
            modelAndView.setViewName("pages/community/faq.html");
        }
        if(page.equals("admin")) {
            modelAndView.setViewName("admin/notice/noticeList.html");
        }

        List<CategoryDTO> category = categoryService.findCategory("faq");
        modelAndView.addObject("category", category);
        modelAndView.addObject("noticeList", noticeDTOList);
        modelAndView.addObject("categoryId", categoryId);

        return modelAndView;
    }

//  ------------------------------------- ★ 관리자페이지 ★ ---------------------------------------------------------------
//  ★ 공지사항_ detail 팝업창 ★ ---------------------------------------------------------------
    @GetMapping("admin/notice/detail")
    public ModelAndView noticeDetail(@RequestParam("noticeId") Integer noticeId) {
        ModelAndView modelAndView = new ModelAndView();
        NoticeDTO noticeDTO = noticeService.noticeList(noticeId);

        modelAndView.addObject("noticeList", noticeDTO);
        modelAndView.setViewName("admin/notice/noticeDetail.html");

        return modelAndView;
    }
//  ★ 공지사항 _ 수정★ ---------------------------------------------------------------
    @GetMapping("admin/notice/change")
    public ModelAndView noticeChangeF(@RequestParam("noticeId") Integer noticeId) {
        ModelAndView modelAndView = new ModelAndView();
        NoticeDTO noticeDTO = noticeService.noticeList(noticeId);

        List<CategoryDTO> category = categoryService.findCategory("faq");
        modelAndView.addObject("category", category);
        modelAndView.addObject("noticeList", noticeDTO);
        modelAndView.setViewName("admin/notice/noticeChange.html");

        return modelAndView;
    }

//  ★ 공지사항 _ 수정 완료★ ---------------------------------------------------------------

    @PostMapping("admin/notice/change")
    public ModelAndView noticeChange(@ModelAttribute NoticeDTO noticeDTO) {
        ModelAndView modelAndView = new ModelAndView();
        Integer noticeId = noticeService.noticeSave(noticeDTO);
        List<CategoryDTO> category = categoryService.findCategory("faq");

        modelAndView.addObject("category", category);

        modelAndView.addObject("data", new Message("수정되었습니다.", "/admin/notice/detail?noticeId="+noticeId));
        modelAndView.setViewName("common/message.html");

        return modelAndView;
    }
//  ★ 공지사항 _ 추가★ ---------------------------------------------------------------

    @GetMapping("admin/notice/add")
    public ModelAndView noticeAddF() {
        ModelAndView modelAndView = new ModelAndView();

        List<CategoryDTO> category = categoryService.findCategory("faq");
        modelAndView.addObject("category", category);
        modelAndView.addObject("noticeDTO", new NoticeDTO());
        modelAndView.setViewName("admin/notice/noticeAdd.html");

        return modelAndView;
    }
    @PostMapping("admin/notice/add")
    public ModelAndView noticeAdd(@ModelAttribute NoticeDTO noticeDTO) {
        ModelAndView modelAndView = new ModelAndView();
        Integer noticeId = noticeService.noticeSave(noticeDTO);
        modelAndView.addObject("data", new Message("추가되었습니다.", "/admin/notice/detail?noticeId="+noticeId));
        modelAndView.setViewName("common/message.html");

        return modelAndView;
    }
//  ★ 공지사항 _ 삭제★ ---------------------------------------------------------------\
    @PostMapping("admin/notice/del")
    public ModelAndView noticeDel(@RequestParam(required = false, name = "noticeId", defaultValue = "0") Integer noticeId,
                                  @RequestParam(required = false, name="noticeIds", defaultValue = "") List<Integer> noticeIds,
                                  @RequestParam(defaultValue = "list", name = "page") String page) {
        ModelAndView modelAndView = new ModelAndView();
        if(noticeIds == null) noticeIds = new ArrayList<>();

        noticeService.delete(noticeId, noticeIds, page);

        if(page.equals("detail"))
            modelAndView.addObject("data", new Message("삭제되었습니다.", "close"));
        else
            modelAndView.addObject("data", new Message("삭제되었습니다.","admin/notice?sort=notice&page=admin"));

        modelAndView.setViewName("common/message.html");
        return modelAndView;
    }

}
