package com.refactoring.rekall.controller;


import com.refactoring.rekall.dto.*;
import com.refactoring.rekall.service.ProductService;
import com.refactoring.rekall.service.UserService;
import com.refactoring.rekall.service.WishListService;
import lombok.AllArgsConstructor;
/*import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.validation.BindingResult;
*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@AllArgsConstructor
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    WishListService wishListService;

//  ---------------------------- ★ 로그인 ★ ---------------------------------------------------------------
    @GetMapping("login")
    public ModelAndView loginf() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/login/login.html");
        return modelAndView;
    }
  /*  public String loginf(){
    return "pages/login/login.html";
    }*/

//   ★ 로그인 완료 ★ ---------------------------------------------------------------
    @PostMapping("login")
    public ModelAndView login(@RequestParam("userId") String userId, @RequestParam("password") String password, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        UserDTO userDTO = userService.findID(userId, password);

        if(userDTO == null) {
            modelAndView.addObject("data", new Message("아이디 또는 비밀번호 오류입니다.", "login"));
            modelAndView.setViewName("common/fragments/message.html");

            System.out.println("로그인 실패");
        } else if(userDTO.getStatus() == "탈퇴계정") {
            modelAndView.addObject("data", new Message("탈퇴한 계정입니다.", "login"));
            modelAndView.setViewName("common/fragments/message.html");

            System.out.println("탈퇴계정");
        }else {
            session.setAttribute("loginId", userId);
            session.setAttribute("userRole", userDTO.getRole());
            modelAndView.addObject("data", new Message("로그인되었습니다.", "main"));
            modelAndView.setViewName("common/fragments/message.html");
            System.out.println("로그인 성공");
        }
        return modelAndView;
    }

//   ★ 로그아웃 ★ ---------------------------------------------------------------
    @GetMapping("logout")
    public ModelAndView logout(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        session.invalidate();

        modelAndView.addObject("data", new Message("로그아웃되었습니다.", "main"));
        modelAndView.setViewName("common/fragments/message.html");

        System.out.println("logout");
        return modelAndView;
    }

//  ---------------------------- ★ 회원가입 ★ -------------------------------------------------------------------------
    @GetMapping("join") // 회원가입
    public ModelAndView joifF() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("userDTO", new UserDTO());
        modelAndView.setViewName("pages/login/join.html");
        return modelAndView;
    }

//   ★ 회원가입 완료 ★ -------------------------------------------------------------------------
    @PostMapping("join") // 회원가입
    public ModelAndView join(@ModelAttribute("userDTO") UserDTO userDTO) {
        ModelAndView modelAndView = new ModelAndView();
        userService.signUp(userDTO);
        modelAndView.addObject("data", new Message("회원가입되었습니다.", "main"));
        modelAndView.setViewName("common/fragments/message.html");

        return modelAndView;
    }

//  ---------------------------- ★ 회원탈퇴 - 정보 이동 ★ -------------------------------------------------------------------------
    @GetMapping("deleteUser")
    public ModelAndView deleteUser(@SessionAttribute(name ="loginId", required = false) String loginId,
                                   UserDelDTO userDelDTO) {
        ModelAndView modelAndView = new ModelAndView();
        userService.deleteUser(loginId, userDelDTO);
        modelAndView.addObject("data", new Message("회원탈퇴되었습니다.", "main"));
        modelAndView.setViewName("common/fragments/message.html");

        return modelAndView;
    }

//  ------------------------------------- ★ My Page ★ -----------------------------------------------------------------

    @GetMapping("mypage") // 마이페이지
    public ModelAndView mypage(@SessionAttribute(name ="loginId", required = false) String loginId,
                               @SessionAttribute(name ="userRole", required = false) String userRole) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("loginId", loginId);
        modelAndView.addObject("userRole", userRole);
        modelAndView.setViewName("pages/mypage/mypage.html");
        return modelAndView;
    }
//   ★ 개인정보수정 ★ ------------------------------------------------------------
    @GetMapping("userInfo") // 마이페이지
    public ModelAndView userInfoF(@SessionAttribute(name ="loginId", required = false) String loginId,
                               @SessionAttribute(name ="userRole", required = false) String userRole) {
        ModelAndView modelAndView = new ModelAndView();
        UserDTO userDTO = userService.findByUserID(loginId);

        modelAndView.addObject("userDTO", userDTO);
        modelAndView.addObject("loginId", loginId);
        modelAndView.addObject("userRole", userRole);
        modelAndView.setViewName("pages/mypage/userInfo.html");
        return modelAndView;
    }
//   ★ 개인정보수정 완료 ★ ------------------------------------------------------------
    @PostMapping("userInfo") // 마이페이지
    public ModelAndView userInfo(@SessionAttribute(name ="loginId", required = false) String loginId,
                                 @SessionAttribute(name ="userRole", required = false) String userRole,
                                 @ModelAttribute("userDTO") UserDTO userDTO) {
        ModelAndView modelAndView = new ModelAndView();
        userService.signUp(userDTO);

        modelAndView.addObject("loginId", loginId);
        modelAndView.addObject("userRole", userRole);

        modelAndView.addObject("data", new Message("수정되었습니다.", "userInfo"));
        modelAndView.setViewName("pages/message.html");
        return modelAndView;
    }


//   ★ wishList ★ ------------------------------------------------------------
    @GetMapping("wishList")
    public ModelAndView wishList(@SessionAttribute(name ="loginId", required = false) String loginId,
                                 @SessionAttribute(name ="userRole", required = false) String userRole) {
        ModelAndView modelAndView = new ModelAndView();

        List<WishListDTO> wishList = wishListService.findWishList(loginId);
        modelAndView.addObject("wishList", wishList);

        modelAndView.addObject("loginId", loginId);
        modelAndView.addObject("userRole", userRole);

        modelAndView.setViewName("pages/mypage/wishList.html");
        return modelAndView;
    }



}
