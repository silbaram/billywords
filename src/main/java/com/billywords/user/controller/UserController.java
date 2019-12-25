package com.billywords.user.controller;


import com.billywords.learning.service.impl.BillyWordsLearningServiceImpl;
import com.billywords.user.service.UserService;
import com.billywords.user.vo.WordUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class UserController {

    @Autowired
    BillyWordsLearningServiceImpl billyWordsLearningService;

    @Autowired
    UserService userService;



    /**
     * 게스트 로그인 페이지(학습하고자 하는 단언 선택)
     * @return
     */
    @RequestMapping(value = "/guest-login", method = RequestMethod.GET)
    public String guestStudyWordsSelect() {

        return "page/guest-login";
    }



    /**
     * 사용자가 단어 학습을 얼만큼 했는지 볼 수 있는 페이지
     * @param model
     * @param wordUser
     * @return
     */
    @RequestMapping( value = "/my-page/ability", method = RequestMethod.GET)
    public String billyAbility(Model model, @AuthenticationPrincipal WordUser wordUser) {
        if(wordUser != null) {

            int learningWordsPosition = billyWordsLearningService.getLearningWordsPosition(wordUser.getUserId());
            model.addAttribute("learningWordsPosition", learningWordsPosition);
        }

        return "page/ability";
    }



    /**
     * 마이 페이지 이동
     * @param model
     * @param wordUser
     * @return
     */
    @RequestMapping(value = "/my-page/personal", method = RequestMethod.GET)
    public String billyPersonal(Model model, @AuthenticationPrincipal WordUser wordUser) {
        if(wordUser != null) {

            int learningWordsPosition = billyWordsLearningService.getLearningWordsPosition(wordUser.getUserId());
            model.addAttribute("learningWordsPosition", learningWordsPosition);
        }

        return "page/personal";
    }



    @RequestMapping(value = "/secession", method = RequestMethod.POST)
    public String secession(@AuthenticationPrincipal WordUser wordUser) {

        userService.secession(wordUser);
        return "redirect:/logout";
    }
}
