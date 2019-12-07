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



    /**
     * 게스트 로그인 페이지(학습하고자 하는 단언 선택)
     * @param model
     * @return
     */
    @RequestMapping(value = "guest-login")
    public String guestStudyWordsSelect(Model model) {

        return "page/guest-login";
    }
}
