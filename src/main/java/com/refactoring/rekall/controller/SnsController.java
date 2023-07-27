package com.refactoring.rekall.controller;

import com.refactoring.rekall.Auth;
import com.refactoring.rekall.service.SnsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Optional;

import static com.refactoring.rekall.Auth.Role.KAKAO;

@Controller
@AllArgsConstructor
@RequestMapping("/login")
public class SnsController {

    @Autowired
    SnsService snsService;

    @GetMapping("/oauth/kakao")
    public String login(@RequestParam("code") String code, HttpSession session) {

        String access_token = snsService.getAccessToken(code);

        HashMap<String,Object> userInfo = snsService.getUserInfo(access_token);
        Object id = userInfo.get("id");
        Object name = userInfo.get("name");
        Object email = userInfo.get("email");

        session.setAttribute("loginId", id);
        session.setAttribute("name", name);
        session.setAttribute("userRole","KAKAO");
        session.setAttribute("access_token", access_token);

        String password="";
        if(snsService.findUser(id, name).equals("F")) { // --> 이미 있는지 확인하기 _ F :F없음 -> 회원가입
            password = snsService.saveSnsUSer((String)id, (String)name, (String) email, KAKAO);
        }

        return "redirect:/main";
        // 지금 session 이 있어서 로그인 되느지 확인은 못했는데, 값이 save 되는 것은 확인됨
    }


}
