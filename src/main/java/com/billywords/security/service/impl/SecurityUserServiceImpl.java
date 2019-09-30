//TODO 나중에 지울지 말지 선택
//package com.billywords.security.service.impl;
//
//
//import com.billywords.security.service.SecurityUserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Service;
//
//
//@Service
//public class SecurityUserServiceImpl implements SecurityUserService {
//
//    @Autowired
//    private UserDetailsService userDetailsServiceImpl;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//
//
//    @Override
//    public String findLoggedInUsername() {
//        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
//        if (userDetails instanceof UserDetails) {
//            return ((UserDetails) userDetails).getUsername();
//        }
//
//        return null;
//    }
//
//
//
//    @Override
//    public void login(String username, String password) {
//        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
//
//        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
//
//        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
//            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//            System.out.printf(String.format("Auto login %s successfully!", password));
//        }
//    }
//}
