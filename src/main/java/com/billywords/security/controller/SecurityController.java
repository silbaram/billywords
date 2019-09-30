package com.billywords.security.controller;

import com.billywords.user.enums.UserType;
import com.billywords.user.service.UserService;
import com.billywords.user.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;



@Controller
public class SecurityController {

    @Autowired
    UserService userService;



    // 로그인
    @RequestMapping("/login")
    public String login(Model model, String error, String logout, HttpServletRequest request ){
        if (logout != null){
            model.addAttribute("logout", "You have been logged out successfully.");
        }
        return "page/login";
    }



    // 회원가입 시 email 유니트 체크
    @ResponseBody
    @RequestMapping(value="/email-unique-check", method= RequestMethod.GET)
    public Map<String, String> emailUniqueCheck(@RequestParam String email){

        return userService.emailUniqueCheck(email);
    }



    // 회원가입폼
    @RequestMapping(value="/registration", method=RequestMethod.GET)
    public String registration(Model model){
        model.addAttribute("userForm", new UserVO());
        return "page/registration";
    }



    // 회원가입 처리
    @RequestMapping(value="/registration",method= RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") UserVO userForm, BindingResult bindingResult, Model model){

        userService.saveUser(userForm, UserType.USER.toString());

        return "redirect:/login";
    }
}
