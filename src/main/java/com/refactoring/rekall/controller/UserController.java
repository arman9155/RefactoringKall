package com.refactoring.rekall.controller;


import com.refactoring.rekall.dto.*;
import com.refactoring.rekall.service.ProductService;
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
        } else if(userDTO.getStatus().equals("탈퇴계정")) {
            modelAndView.addObject("data", new Message("탈퇴한 계정입니다.", "login"));
            modelAndView.setViewName("common/fragments/message.html");

        }else {
            session.setAttribute("loginId", userId);
            session.setAttribute("userRole", userDTO.getRole());
            modelAndView.addObject("data", new Message("로그인되었습니다.", "main"));
            modelAndView.setViewName("common/fragments/message.html");
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
        modelAndView.addObject("data", new Message("회원가입되었습니다.", "login"));
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
    @GetMapping("u_userInfo") // 마이페이지
    public ModelAndView userInfoF(@SessionAttribute(name ="loginId", required = false) String loginId,
                               @SessionAttribute(name ="userRole", required = false) String userRole) {
        ModelAndView modelAndView = new ModelAndView();
        UserDTO userDTO = userService.findByUserID(loginId);

        modelAndView.addObject("loginId", loginId);
        modelAndView.addObject("userRole", userRole);
        modelAndView.addObject("userDTO", userDTO);
        modelAndView.setViewName("pages/mypage/userInfo.html");
        return modelAndView;
    }
//   ★ 개인정보수정 완료 ★ ------------------------------------------------------------
    @PostMapping("u_userInfo") // 마이페이지
    public ModelAndView userInfo(@SessionAttribute(name ="loginId", required = false) String loginId,
                                 @SessionAttribute(name ="userRole", required = false) String userRole,
                                 @ModelAttribute("userDTO") UserDTO userDTO) {
        ModelAndView modelAndView = new ModelAndView();
        userService.signUp(userDTO);

        modelAndView.addObject("loginId", loginId);
        modelAndView.addObject("userRole", userRole);

        modelAndView.addObject("data", new Message("수정되었습니다.", "u_userInfo"));
        modelAndView.setViewName("common/fragments/message.html");
        return modelAndView;
    }
    //   ★ 회원 탈퇴 하기 ★ -----------------------------------------------------------------
    @GetMapping("u_deleteUser")
    public ModelAndView u_deleteUserF(@SessionAttribute(name ="loginId", required = false) String loginId) {
        ModelAndView modelAndView = new ModelAndView();

/*         UserDelDTO userDelDTO = new UserDelDTO();
        userDelDTO.setUserDTO(userService.findByUserID(loginId));
        System.out.println("userDelDTO"+userDelDTO.getUserDTO().getStatus());
        System.out.println("userDelDTO"+userDelDTO.getUserDTO().getName());
        System.out.println("userDelDTO"+userDelDTO.getUserDTO().getPassword());
        System.out.println("userDelDTO"+userDelDTO.getUserDTO().getEmail());
        System.out.println("userDelDTO"+userDelDTO.getUserDTO());*/

        modelAndView.addObject("userDelDTO", new UserDelDTO());
        modelAndView.addObject("loginId", loginId);
        modelAndView.setViewName("pages/mypage/deleteUser.html");

        return modelAndView;
    }

    @PostMapping("u_deleteUser")
    public ModelAndView u_deleteUser(@ModelAttribute("userDelDTO") UserDelDTO userDelDTO, HttpSession session) {

        userService.deleteUser(userDelDTO, userDelDTO.getUserDTO().getUserId());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("data",new Message("탈퇴되었습니다.", "close"));
        modelAndView.setViewName("common/fragments/message.html");
        session.invalidate();

        return modelAndView;
    }

//  ------------------------------------- ★ 관리자페이지 ★ -----------------------------------------------------------------
//   ★ 회원 리스트 ★ -----------------------------------------------------------------

    @GetMapping("a_user")
    public ModelAndView userList(@RequestParam(value="sort", defaultValue = "all") String status) {
        ModelAndView modelAndView = new ModelAndView();
        List<UserDTO> userList = userService.getList(status);

        modelAndView.addObject("userList", userList);
        modelAndView.addObject("status", status);
        modelAndView.setViewName("admin/user/userList.html");

        return modelAndView;
    }

//   ★ 회원 탈퇴 처리 ★ -----------------------------------------------------------------

    @PostMapping("a_deleteUser")
    public ModelAndView a_deleteUser(@RequestParam("userIds") List<String> userIds) {
        userService.a_deleteUser(userIds);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("data",new Message("탈퇴되었습니다.", "a_user"));
        modelAndView.setViewName("common/fragments/message.html");

        return modelAndView;
    }

}
