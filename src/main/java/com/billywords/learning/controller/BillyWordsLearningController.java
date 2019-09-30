package com.billywords.learning.controller;

import com.billywords.learning.service.impl.BillyWordsLearningServiceImpl;
import com.billywords.user.vo.WordUser;
import com.billywords.words.models.LearningWordsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
public class BillyWordsLearningController {

    @Autowired
    BillyWordsLearningServiceImpl billyWordsLearningService;



    // 단어 테스트 페이지
    @RequestMapping("/words-test")
    public String login(Model model, String error, String logout, HttpServletRequest request, @AuthenticationPrincipal WordUser wordUser){

        LearningWordsEntity learningWordsEntity = null;

        //사용자 정보가 없다면 비회면 로직으로 보여준다.
        if(wordUser == null) {
            learningWordsEntity = billyWordsLearningService.getLearningWordsEntity(0, true);

        } else {
            learningWordsEntity = billyWordsLearningService.getLearningWordsEntity(wordUser.getUserId(), true);


            System.out.println("learningWordsEntity : " + learningWordsEntity.getExampleEntityList());

            if(learningWordsEntity.getExampleEntityList().size() == 0) {
                System.out.println("보기 만들기");
                billyWordsLearningService.createWordExample(wordUser.getUserId());
            }
        }

        return "page/words-test";
    }
}
