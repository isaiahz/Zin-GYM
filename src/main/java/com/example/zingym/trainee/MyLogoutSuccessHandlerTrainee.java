package com.example.zingym.trainee;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component("myLogoutSuccessHandlerTrainee")
public class MyLogoutSuccessHandlerTrainee implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (session != null){
            session.removeAttribute("user");
        }

        if (response.isCommitted()) {
            System.out.println("Can't redirect");
        }
        else {
            response.sendRedirect("/trainee/login");

        }

}}