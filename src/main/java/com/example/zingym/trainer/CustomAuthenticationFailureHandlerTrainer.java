package com.example.zingym.trainer;

import org.springframework.security.core.AuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFailureHandlerTrainer implements org.springframework.security.web.authentication.AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        if (e.getMessage().equals("User is disabled")) {
            httpServletResponse.sendRedirect("/trainer/login?error=disabled");
        } else {
            httpServletResponse.sendRedirect("/trainer/login?error=invalid");
        }

    }
}
