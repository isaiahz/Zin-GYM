package com.example.zingym.trainer;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandlerTrainer extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        CustomTrainerDetails customUserDetails = (CustomTrainerDetails) authentication.getPrincipal();


        String redirectUrl = request.getContextPath();

        if (customUserDetails.isEnabled()) {
            redirectUrl += "/trainer";
        }

        else {
            redirectUrl += "/";
        }

        response.sendRedirect(redirectUrl);
    }
}
