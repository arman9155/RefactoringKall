package com.refactoring.rekall;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@Getter
@RequestMapping("")
public class MainController {

    //  ------------------------------------- ★ Product List ★ ---------------------------------------------------------------
    @GetMapping(value = {"", "main"})
    public ModelAndView main() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/main.html");
        return modelAndView;
    }

    @GetMapping("customList") // 상품리스트 _ custom
    public ModelAndView customList() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/product/customList.html");
        return modelAndView;
    }

    @GetMapping("designList") // 상품리스트 _ desingn
    public ModelAndView designList() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/product/designList.html");
        return modelAndView;
    }

    @GetMapping("etcList") // 상품리스트 _ etc
    public ModelAndView etcList() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/product/etcList.html");
        return modelAndView;
    }

    //  ------------------------------------- ★ Community ★ ---------------------------------------------------------------
    @GetMapping("notice") // 공지사항
    public ModelAndView noticeList() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/community/notice.html");
        return modelAndView;
    }

    @GetMapping("road") // 찾아오시는 길
    public ModelAndView road() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/community/road.html");
        return modelAndView;
    }

    @GetMapping("qna") // 자주 묻는 질문
    public ModelAndView qna() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/community/qna.html");
        return modelAndView;
    }

    @GetMapping("question") // 1:1 문의
    public ModelAndView question() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/community/question.html");
        return modelAndView;
    }

    @GetMapping("review") // 후기
    public ModelAndView review() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/community/review.html");
        return modelAndView;
    }


//  ------------------------------------- ★ My Page ★ ---------------------------------------------------------------
    @GetMapping("login") // 로그인
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/login/login.html");
        return modelAndView;
    }

    @PostMapping("login") // 로그인
    public ModelAndView loginOK() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/main.html");
        return modelAndView;
    }

    @GetMapping("join") // 로그인
    public ModelAndView join() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/login/join.html");
        return modelAndView;
    }

}