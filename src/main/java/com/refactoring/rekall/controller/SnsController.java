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

/*
    @GetMapping("kakaologin")
    public String kakao() {
        return "kakaologin";
    }
*/

    @GetMapping("/oauth/kakao")
    public String login(@RequestParam("code") String code, HttpSession session) {
        System.out.println("code: "+code);
        String access_token = snsService.getAccessToken(code);
        System.out.println("access_token: 여기까지가 access token 가져오기"+access_token);

        HashMap<String,Object> userInfo = snsService.getUserInfo(access_token);
        Object id = userInfo.get("id");
        Object name = userInfo.get("name");
        Object email = userInfo.get("email");

        session.setAttribute("loginId", id);
        session.setAttribute("name", name);
        session.setAttribute("userRole","kakao");
        session.setAttribute("access_token", access_token);

        System.out.println("session >>> " + session.getAttribute("loginId"));
        System.out.println("session >>> " + session.getAttribute("name"));
        System.out.println("session >>> " + session.getAttribute("email"));

        String password="";
        if(snsService.findUser(id, name).equals("F")) { // --> 이미 있는지 확인하기 _ F :F없음 -> 회원가입
            password = snsService.saveSnsUSer((String)id, (String)name, (String) email, KAKAO);
        }

        System.out.println("userInfo >>> "+userInfo);
        return "redirect:/main";
        // 지금 session 이 있어서 로그인 되느지 확인은 못했는데, 값이 save 되는 것은 확인됨
    }


}
