package com.billywords.user.controller;


import com.billywords.learning.service.impl.BillyWordsLearningServiceImpl;
import com.billywords.user.vo.UserVO;
import com.billywords.user.vo.WordUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;


@Controller
public class UserController {

    @Autowired
    BillyWordsLearningServiceImpl billyWordsLearningService;



    /**
     * 게스트 로그인 페이지(학습하고자 하는 단언 선택)
     * @return
     */
    @RequestMapping(value = "guest-login")
    public String guestStudyWordsSelect() {

        return "page/guest-login";
    }


    @RequestMapping( value = "ability")
    public String billyAbility(Model model, @AuthenticationPrincipal WordUser wordUser) {
        if(wordUser != null) {

            int learningWordsPosition = billyWordsLearningService.getLearningWordsPosition(wordUser.getUserId());
            model.addAttribute("learningWordsPosition", learningWordsPosition);
        }

        return wordUser != null ? "page/ability" : "redirect:login";
    }
}
