package com.refactoring.rekall.interceptor;

import com.refactoring.rekall.dto.UserDTO;
import com.refactoring.rekall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("어드민 컨트롤러 요청 입니다.!!");
        //그냥 admin 페이지 다 못들어가게 하면 되는거 아냐?
//
//        // 1. handler 종류 확인
//        // 우리가 관심 있는 것은 Controller에 있는 메서드이므로 HandlerMethod 타입인지 체크
//        if( handler instanceof HandlerMethod == false ) {
//            // return true이면  Controller에 있는 메서드가 아니므로, 그대로 컨트롤러로 진행
//            return true;
//        }
//
//        // 2.형 변환
//        HandlerMethod handlerMethod = (HandlerMethod)handler;
//
//        // 3. @Auth 받아오기
//        Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
//        String role = auth.role().toString();
//
//        // 4. method에 @Auth가 없는 경우, 즉 인증이 필요 없는 요청
//        if( auth == null ) {
//            return true;
//        }
//
//        // 5. @Auth가 있는 경우이므로, 세션이 있는지 체크
//        HttpSession session = request.getSession();
//        String loginId = (String) session.getAttribute("loginId");
//
//        // 7. admin일 경우
//        if(role  != null) {
//            if( role.equals(Auth.Role.ADMIN)) {
//                System.out.println("role.equals여기");
//                UserDTO userDTO = userService.findByUserID(loginId);
//                // admin임을 알 수 있는 조건을 작성한다.
//                // ex) 서비스의 id가 root이면 admin이다.
//                if ("ADMIN".equals(userDTO.getRole()) == false) {   // admin이 아니므로 return false
//                    response.sendRedirect("/noPermission");
//                    return false;
//                }
//            } else {
//                response.sendRedirect("/noPermission");
//                return false;
//            }
//        }
//
//        // 8. 접근허가, 즉 메서드를 실행하도록 함
//        return true;
//    }

        HttpSession session = request.getSession();
        String userRole = session.getAttribute("userRole").toString();
        String loginId = session.getAttribute("loginId").toString();
        
        System.out.println("loginId"+loginId+userRole);

        // loginId의 role이 admin일 경우
          if(userRole.equals("ADMIN")) {
            System.out.println("role.equals여기");
//            UserDTO userDTO = userService.findByUserID(loginId);
//            --> 이게 왜 null 로 나오냐
            // admin임을 알 수 있는 조건을 작성한다.
            // ex) 서비스의 id가 root이면 admin이다.
/*            if (ADMIN.equals(userDTO.getRole()) == false) {   // admin이 아니므로 return false
                response.sendRedirect("/noPermission");
                return false;
            }*/
        } else { // admin 이 아닌 경우
            response.sendRedirect("/noPermission");
            return false;
        }

        return true;
    }

}
