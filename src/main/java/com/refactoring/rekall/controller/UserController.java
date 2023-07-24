package com.refactoring.rekall.controller;


import com.refactoring.rekall.Auth;
import com.refactoring.rekall.dto.*;
import com.refactoring.rekall.service.SnsService;
import com.refactoring.rekall.service.UserService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.refactoring.rekall.Auth.Role.ADMIN;
import static com.refactoring.rekall.Auth.Role.KAKAO;

@Controller
@AllArgsConstructor
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    SnsService snsService;


    @GetMapping("login/Info")
    public ModelAndView loginInfo() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("data", new Message("로그인 후 사용해주세요.", "/login"));
        modelAndView.setViewName("/common/message.html");
        return modelAndView;
    }
    @GetMapping("noPermission")
    public ModelAndView noAdmin() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("data", new Message("사용 권한이 없습니다.", "/main"));
        modelAndView.setViewName("common/message.html");
        return modelAndView;
    }

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
            modelAndView.addObject("data", new Message("아이디 또는 비밀번호 오류입니다.", "/login"));
            modelAndView.setViewName("common/message.html");

            System.out.println("로그인 실패");
        } else if(userDTO.getStatus().equals("탈퇴계정")) {
            modelAndView.addObject("data", new Message("탈퇴한 계정입니다.", "/login"));
            modelAndView.setViewName("common/message.html");

        }else {
            session.setAttribute("loginId", userId);
            session.setAttribute("userRole", userDTO.getRole().toString());
            modelAndView.addObject("data", new Message("로그인되었습니다.", "/main"));
            modelAndView.setViewName("common/message.html");
        }
        return modelAndView;
    }

//   ★ 로그아웃 ★ ---------------------------------------------------------------
    @GetMapping("logout")
    public ModelAndView logout(HttpSession session, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();

        session.invalidate();
        modelAndView.addObject("data", new Message("로그아웃되었습니다.", "/main"));
        modelAndView.setViewName("common/message.html");
        System.out.println("logout");

        Cookie myCookie = new Cookie("JSESSIONID", null);  // 쿠키 값을 null로 설정
        myCookie.setMaxAge(0);  // 남은 만료시간을 0으로 설정
        response.addCookie(myCookie);
        System.out.println("jsessionId"+myCookie);

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

// ID 중복 확인
    @GetMapping("join/idCheckF")
    public ModelAndView idDupCheckF(@RequestParam("id") String id) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("writeId", id);
        modelAndView.setViewName("pages/login/idDupCheck.html");
        return modelAndView;
    } //idDupCheck

    @GetMapping("join/idDupCheck")
    public ModelAndView idDupCheck(@RequestParam("id") String id) {
        ModelAndView modelAndView = new ModelAndView();

        String status = userService.idDupC(id);
        System.out.println("status"+status);
        modelAndView.addObject("writeId", id);
        modelAndView.addObject("idUse", status);
        modelAndView.setViewName("pages/login/idDupCheck.html");
        return modelAndView;
    } //idDupCheck

    //   ★ 회원가입 완료 ★ -------------------------------------------------------------------------
    @PostMapping("join") // 회원가입
    public ModelAndView join(@ModelAttribute("userDTO") UserDTO userDTO) {
        ModelAndView modelAndView = new ModelAndView();
        String status = userService.signUp(userDTO);
        if("T".equals(status)) {
            modelAndView.addObject("data", new Message("회원가입되었습니다.", "/login"));
            modelAndView.setViewName("common/message.html");
        } else {
            modelAndView.addObject("data", new Message("다시 가입해주세요.", "/join"));
            modelAndView.setViewName("common/message.html");
        }

        return modelAndView;
    }

//  ------------------------------------- ★ My Page ★ -----------------------------------------------------------------

    @GetMapping("mypage") // 마이페이지
    public ModelAndView mypage() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("pages/mypage/mypage.html");
        return modelAndView;
    }

//   ★ 개인정보수정 ★ ------------------------------------------------------------
    @GetMapping("/mypage/user/info") // 마이페이지
    public ModelAndView userInfoF(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        String loginId = session.getAttribute("loginId").toString();

        UserDTO userDTO = userService.findByUserID(loginId);
        modelAndView.addObject("userDTO", userDTO);
        modelAndView.setViewName("pages/mypage/profile/userInfo.html");
        return modelAndView;
    }
//   ★ 개인정보수정 완료 ★ ------------------------------------------------------------
    @PostMapping("/mypage/user/info") // 마이페이지
    public ModelAndView userInfo(@ModelAttribute("userDTO") UserDTO userDTO) {
        ModelAndView modelAndView = new ModelAndView();
        userService.signUp(userDTO);

        modelAndView.addObject("data", new Message("수정되었습니다.", "/mypage/user/info"));
        modelAndView.setViewName("common/message.html");
        return modelAndView;
    }
    //   ★ 회원 탈퇴 하기 ★ -----------------------------------------------------------------
    @GetMapping("/mypage/user/deleter")
    public ModelAndView u_deleteUserF() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("userDelDTO", new UserDelDTO());
        modelAndView.setViewName("pages/mypage/profile/deleteUser.html");

        return modelAndView;
    }

    @PostMapping("/mypage/user/delete")
    public ModelAndView u_deleteUser(@ModelAttribute("userDelDTO") UserDelDTO userDelDTO, HttpSession session) {

        userService.deleteUser(userDelDTO, session.getAttribute("loginId").toString());
        ModelAndView modelAndView = new ModelAndView();

        if(session.getAttribute("userRole").equals(KAKAO)) {
            snsService.deleteKakao((String)session.getAttribute("access_token"));
        }

        modelAndView.addObject("data",new Message("탈퇴되었습니다.", "close"));
        modelAndView.setViewName("common/message.html");
        session.invalidate();
//탈퇴되엇습니다 -> 창 close -> main으로 이동해야함  ==> close 수정중이었는데 안됭
        return modelAndView;
    }


//  ------------------------------------- ★ 관리자페이지 ★ -----------------------------------------------------------------
//   ★ 회원 리스트 ★ -----------------------------------------------------------------

    @GetMapping("admin/user")
    public ModelAndView userList(@RequestParam(value="sort", defaultValue = "all") String status) {
        ModelAndView modelAndView = new ModelAndView();
        List<UserDTO> userList = userService.getList(status);

        modelAndView.addObject("userList", userList);
        modelAndView.addObject("status", status);
        modelAndView.setViewName("admin/user/userList.html");

        return modelAndView;
    }

//   ★ 회원 탈퇴 처리 ★ -----------------------------------------------------------------

    @PostMapping("admin/user/del")
    public ModelAndView a_deleteUser(@RequestParam("userIds") List<String> userIds) {
        userService.a_deleteUser(userIds);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("data",new Message("탈퇴되었습니다.", "/admin/user"));
        modelAndView.setViewName("common/message.html");

        return modelAndView;
    }

}
