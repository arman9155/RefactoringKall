package com.refactoring.rekall.controller;

import com.refactoring.rekall.dto.CategoryDTO;
import com.refactoring.rekall.dto.Message;
import com.refactoring.rekall.dto.UsQDTO;
import com.refactoring.rekall.service.CategoryService;
import com.refactoring.rekall.service.UsQService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class UsQController {

    @Autowired
    UsQService usQService;
    @Autowired
    CategoryService categoryService;

//  ---------------------------- ★ 1:1 문의 정렬 ★ ---------------------------------------------------------------
    @GetMapping("question") // 1:1 문의
    public ModelAndView questionF(@SessionAttribute(name ="loginId", required = false) String loginId,
                                  @SessionAttribute(name ="userRole", required = false) String userRole) {

        ModelAndView modelAndView = new ModelAndView();
        String email = usQService.findEmail(loginId);

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
        usQService.saveQuestion(usQDTO);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("data",new Message("완료되었습니다.", "question"));
        modelAndView.setViewName("common/fragments/message.html");

        return modelAndView;
    }

//  ---------------------------- ★ userID_질문 리스트 --관리자포함 ★ ---------------------------------------------------------------
    @GetMapping("questionList") // 1:1 문의
    public ModelAndView u_questionList(@SessionAttribute(name ="loginId", required = false) String loginId,
                                        @SessionAttribute(name ="userRole", required = false) String userRole,
                                       @RequestParam("page") String page,
                                       @RequestParam(name="sort", defaultValue = "all", required = false) String categoryId) {
        ModelAndView modelAndView = new ModelAndView();
        List<UsQDTO> usQList = new ArrayList<>();

        if(page.equals("mypage")) {
            usQList = usQService.usqList(loginId);

            modelAndView.addObject("loginId", loginId);
            modelAndView.addObject("userRole", userRole);
            modelAndView.setViewName("pages/mypage/questionList.html");
        } else {
            if(categoryId.equals("all"))
                usQList = usQService. usqAllList();
            else
                usQList = usQService.findCList(categoryId);
            List<CategoryDTO> categoryDTOL = categoryService.findCategory("q_");
            List<CategoryDTO> categoryDTOList = new ArrayList<>();
            for(CategoryDTO categoryDTO : categoryDTOL) {
                if(categoryDTO != null) {
                    if (!categoryDTO.getCategoryId().contains("faq"))
                        categoryDTOList.add(categoryDTO);
                }
            }
            modelAndView.addObject("category", categoryDTOList);
            modelAndView.addObject("categoryId", categoryId);
            modelAndView.setViewName("admin/question/questionList.html");
        }
        modelAndView.addObject("usQList", usQList);

        return modelAndView;
    }

//  ---------------------------- ★ userID_질문 삭제  -- 관리자포함★ ---------------------------------------------------------------
    @PostMapping("deleteUsQ")
    public ModelAndView deleteUsQ(@RequestParam(name="usqIds", required = false, defaultValue = "") List<Integer> usQIds,
                                  @RequestParam(name= "questionId", required = false, defaultValue = "0") Integer usqId,
                                  @RequestParam("page") String page) {
        if(usQIds == null) usQIds = new ArrayList<>();

        usQService.deleteUsQ(usQIds, usqId, page);
        ModelAndView modelAndView = new ModelAndView();

        if(page.equals("mypage"))
            modelAndView.addObject("data",new Message("삭제되었습니다.", "questionList?page=mypage"));
        else if(page.equals("detail"))
            modelAndView.addObject("data", new Message("삭제되었습니다.", "close"));
        else
            modelAndView.addObject("data",new Message("삭제되었습니다.", "questionList?page=admin"));

        modelAndView.setViewName("common/fragments/message.html");

        return modelAndView;
    }

//  ★ Detail ★ ---------------------------------------------------------------

    @GetMapping("question_Detail") // 1:1 문의
    public ModelAndView questionDetail(@RequestParam("questionId") Integer qId) {

        ModelAndView modelAndView = new ModelAndView();
        UsQDTO usQDTO = usQService.findUsQ(qId);

        modelAndView.addObject("question", usQDTO);
        modelAndView.setViewName("admin/question/questionDetail.html");

        return modelAndView;
    }

//  ★ 저장 ★ ---------------------------------------------------------------

    @PostMapping("question_comment") // 1:1 문의
    public ModelAndView questionSave(@ModelAttribute UsQDTO question) {

        ModelAndView modelAndView = new ModelAndView();

        usQService.saveQuestion(question);

        modelAndView.addObject("data",new Message("답변이 등록되었습니다.","question_Detail?questionId="+question.getUsqId()));
        modelAndView.setViewName("common/fragments/message.html");

        return modelAndView;
    }

}
