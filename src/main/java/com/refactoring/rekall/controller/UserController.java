package com.refactoring.rekall.controller;
/*
import com.refactoring.rekall.dto.UserDTO;
import com.refactoring.rekall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@Transactional
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

//  ------------------------------------- ★ login ★ -------------------------------------------------------------------
    @GetMapping("login") // 로그인
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/login/login.html");
    return modelAndView;
}

    @PostMapping("login") // 로그인 완료
    public ModelAndView loginOK() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/main.html");
        return modelAndView;
    }

    @GetMapping("join") // 회원가입
    public ModelAndView join() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userDTO", new UserDTO());
        modelAndView.setViewName("pages/login/join.html");
        return modelAndView;
    }

    @PostMapping("join")
    public String joinOk(@Valid UserDTO userDTO, BindingResult bindingResult, ModelAndView modelAndView) {
        if(bindingResult.hasErrors()) {
            return "/pages/login/join.html";
        }
        try {
            UserDTO userDTO1 = UserDTO.createUser(userDTO, passwordEncoder);
            userService.save(userDTO1);
        } catch (IllegalStateException e) {
            modelAndView.addObject("errorMessage", e.getMessage());
            return "/pages/login/join.html";
        }
        return "redirect:/pages/login/login.html";
    }
}
1번  */

import com.refactoring.rekall.dto.UserDTO;
import com.refactoring.rekall.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @GetMapping("login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/login/login.html");
    return modelAndView;
    }

    @GetMapping("join") // 회원가입
    public ModelAndView joifF() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("userDTO", new UserDTO());
        modelAndView.setViewName("pages/login/join.html");
        return modelAndView;
    }

    @PostMapping("join") // 회원가입
    public String join(UserDTO userDTO) {
        ModelAndView modelAndView = new ModelAndView();
        userService.signUp(userDTO);

        return "redirect:pages/login/login";
    }
}
