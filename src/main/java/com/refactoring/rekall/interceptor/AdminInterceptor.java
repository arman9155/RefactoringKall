package com.refactoring.rekall.interceptor;

import com.refactoring.rekall.dto.UserDTO;
import com.refactoring.rekall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        String userRole = session.getAttribute("userRole").toString();
        String loginId = session.getAttribute("loginId").toString();

        if(!userRole.equals("ADMIN")) {
            response.sendRedirect("/noPermission");
            return false;
        }

        return true;
    }

}
