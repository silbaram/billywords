package com.billywords.config.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFailure implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

//        httpServletRequest.setAttribute("username", httpServletRequest.getParameter("username"));
//        httpServletRequest.getRequestDispatcher("/loginError").forward(httpServletRequest, httpServletResponse);
        httpServletResponse.sendRedirect("/login?error=true");
    }
}
