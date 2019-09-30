package com.billywords.user.controller;


import com.billywords.user.vo.UserVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;


@Controller
public class UserController {

//    @Autowired
//    UserService userService;

//    @Autowired
//    SecurityUserService securityUserService;



//    // 로그인
//    @RequestMapping("/login")
//    public String login(Model model, String error, String logout, HttpServletRequest request ){
//        if (logout != null){
//            model.addAttribute("logout", "You have been logged out successfully.");
//        }
//        return "page/login";
//    }

//    // 로그인 실패시
//    @RequestMapping(value="/loginError")
//    public String loginError(Model model, String username ){
//        model.addAttribute("error", "Your username and password is invalid.");
//        model.addAttribute("username",username);
//        return "login.html";
//    }

//    // 회원가입폼
//    @RequestMapping(value="/registration", method=RequestMethod.GET)
//    public String registration(Model model){
//        model.addAttribute("userForm", new UserVO());
//        return "page/registration";
//    }
}
