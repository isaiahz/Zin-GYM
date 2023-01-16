package com.example.zingym.admin;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AdminLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        CustomAdminDetails customAdminDetails = (CustomAdminDetails) authentication.getPrincipal();


        String redirectUrl = request.getContextPath();

        if (customAdminDetails.isEnabled()) {
            redirectUrl += "/admin";
        }

        else {
            redirectUrl += "/";
        }

        response.sendRedirect(redirectUrl);
    }
}
