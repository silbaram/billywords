package com.billywords.learning.controller;

import com.billywords.cost.CommonCode;
import com.billywords.learning.service.impl.BillyWordsLearningServiceImpl;
import com.billywords.learning.vo.WordsProblemVO;
import com.billywords.user.vo.WordUser;
import com.billywords.words.models.ExampleEntity;
import com.billywords.words.models.LearningWordsEntity;
import com.billywords.words.models.WordSpellingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping(value = "/words-test")
public class BillyWordsLearningController {

    @Autowired
    BillyWordsLearningServiceImpl billyWordsLearningService;



    /**
     * 단어 테스트 페이지
     * @param model
     * @param wordUser
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String login(Model model, @AuthenticationPrincipal WordUser wordUser){

        //학습중인 단어
        LearningWordsEntity learningWordsEntity = null;

        //사용자 정보가 없다면 비회면 로직으로 보여준다.
        if(wordUser == null) {
            //TODO 비로그인 사용자를 위한 문제 풀기를 만들어야됨
            learningWordsEntity = billyWordsLearningService.getLearningWordsEntity(0, true);
        } else {
            learningWordsEntity = billyWordsLearningService.getLearningWordsEntity(wordUser.getUserId(), true);

            if(learningWordsEntity.getExampleEntityList().size() == 0) {
                //학습을 위한 문제의 보기를 만든다
                List<ExampleEntity> exampleEntityList = billyWordsLearningService.createWordExample(wordUser.getUserId(), learningWordsEntity);
                model.addAttribute("learningWordsExampleList", exampleEntityList);
            } else {
                model.addAttribute("learningWordsExampleList", learningWordsEntity.getExampleEntityList());
            }
        }

        List<WordSpellingEntity> wordSpellingEntityList = learningWordsEntity.getWordsGroupEntity().getWordSpellingEntityList();
        //TODO 학습을 하기 위한 언어를 선택 하고 가져와서 문제를 어떤 언어로 출제 할지 선택 하는 부분이 필요
        Optional<WordSpellingEntity> returnWordSpellingEntityOptional = wordSpellingEntityList.stream().filter(x -> x.getLanguageCode().equals("EN")).findFirst();

        model.addAttribute("learningWord", returnWordSpellingEntityOptional.isPresent() ? returnWordSpellingEntityOptional.get().getWordSpelling() : "");
        model.addAttribute("part", learningWordsEntity.getWordsGroupEntity().getPartsOfSpeech());

        return "page/words-test";
    }



    /**
     * 문제 풀기 ajax
     * @param wordsProblem
     * @param model
     * @param wordUser
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/exam-question", method = RequestMethod.PATCH)
    public ResponseEntity<?> wordsExamQuestion(@RequestBody WordsProblemVO wordsProblem, Model model, @AuthenticationPrincipal WordUser wordUser) {

        boolean isNextExample = billyWordsLearningService.isWordQuestionCorrect(wordUser.getUserId(), wordsProblem);
        wordsProblem.setNextExample(isNextExample);

        return ResponseEntity.ok(wordsProblem);
    }



    /**
     * 다음 문제 만들기 요청
     * @param model
     * @param wordUser
     * @return
     */
    @RequestMapping(value = "/next/example", method = RequestMethod.POST)
    public String wordsNextExample(Model model, @AuthenticationPrincipal WordUser wordUser) {

        // 새로 학습할 단어
        billyWordsLearningService.nextLearningWordsEntity(wordUser);

        return "redirect:/words-test";
    }


    //학습중인 문제를 완전히 익혔을때 그 문제르 빼고 다음 문제 가져오기
    @RequestMapping(value = "/next/exam-question", method = RequestMethod.POST)
    public String createNextWordsExamQuestion(Model model, @AuthenticationPrincipal WordUser wordUser) {

        billyWordsLearningService.createNextLearningWordsEntity(wordUser);

        return "redirect:/words-test";
    }
}
