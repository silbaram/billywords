package com.billywords.learning.controller;

import com.billywords.cost.CommonCode;
import com.billywords.learning.service.impl.BillyWordsLearningServiceImpl;
import com.billywords.learning.vo.WordsProblemVO;
import com.billywords.user.vo.WordUser;
import com.billywords.words.models.ExampleEntity;
import com.billywords.words.models.LearningWordsEntity;
import com.billywords.words.models.WordSpellingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${word.example.guest.id}")
    private int guestId;


    /**
     * 단어 테스트 페이지
     * @param model
     * @param wordUser
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String login(Model model, @AuthenticationPrincipal WordUser wordUser){

        String page = "page/words-test";
        //학습중인 단어
        LearningWordsEntity learningWordsEntity;

        //사용자 정보가 없다면 비회면 로직으로 보여준다.
        if(wordUser == null) {
            List<LearningWordsEntity> learningWordsEntityList= billyWordsLearningService.getGuestLearningWordsEntityList();
            learningWordsEntity = learningWordsEntityList.get(0);

            //튜토리얼 학습을 위한 문제의 임시보기를 만든다
            List<ExampleEntity> exampleEntityList = billyWordsLearningService.createGuestWordExample(learningWordsEntity);
            model.addAttribute("learningWordsExampleList", exampleEntityList);
            model.addAttribute("learningWordsPosition", 1);
            model.addAttribute("learningWordsGroupEntityId", learningWordsEntity.getWordsGroupEntity().getId());
            page = "page/guest-words-test";
        } else {
            //학습중인 단어
            learningWordsEntity = billyWordsLearningService.getLearningWordsEntity(wordUser.getUserId(), true);

            if(learningWordsEntity.getExampleEntityList().size() == 0) {
                //학습을 위한 문제의 보기를 만든다
                List<ExampleEntity> exampleEntityList = billyWordsLearningService.createWordExample(wordUser.getUserId(), wordUser.getFromLanguage(), wordUser.getToLanguage(), learningWordsEntity);
                model.addAttribute("learningWordsExampleList", exampleEntityList);
            } else {
                model.addAttribute("learningWordsExampleList", learningWordsEntity.getExampleEntityList());
            }
        }

        List<WordSpellingEntity> wordSpellingEntityList = learningWordsEntity.getWordsGroupEntity().getWordSpellingEntityList();
        Optional<WordSpellingEntity> returnWordSpellingEntityOptional = wordSpellingEntityList.stream().filter(x -> x.getLanguageCode().equals(wordUser.getToLanguage())).findFirst();

        model.addAttribute("learningWord", returnWordSpellingEntityOptional.isPresent() ? returnWordSpellingEntityOptional.get().getWordSpelling() : "");
        model.addAttribute("part", learningWordsEntity.getWordsGroupEntity().getPartsOfSpeech());

        return page;
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

        //게스트 일 경우 문제 풀기
        if(wordUser == null) {
            boolean isNextExample = billyWordsLearningService.isGuestWordQuestionCorrect(wordsProblem);
            wordsProblem.setNextExample(isNextExample);
        } else {
            boolean isNextExample = billyWordsLearningService.isWordQuestionCorrect(wordUser.getUserId(), wordsProblem);
            wordsProblem.setNextExample(isNextExample);
        }

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



    /**
     * 다음 문제 만들기 요청 (비회원용 튜토리얼)
     * @param model
     * @return
     */
    @RequestMapping(value = "/next/guest-example", method = RequestMethod.POST)
    public String guestWordsNextExample(@RequestParam String learningWordsPosition, Model model) {

        List<LearningWordsEntity> learningWordsEntityList = billyWordsLearningService.getGuestLearningWordsEntityList();
        LearningWordsEntity learningWordsEntity = learningWordsEntityList.get(Integer.valueOf(learningWordsPosition.trim()));

        //튜토리얼 학습을 위한 문제의 임시보기를 만든다
        List<ExampleEntity> exampleEntityList = billyWordsLearningService.createGuestWordExample(learningWordsEntity);
        model.addAttribute("learningWordsExampleList", exampleEntityList);
        model.addAttribute("learningWordsEntityList", learningWordsEntityList.toArray());
        model.addAttribute("learningWordsPosition", Integer.valueOf(learningWordsPosition.trim()) + 1);
        model.addAttribute("learningWordsGroupEntityId", learningWordsEntity.getWordsGroupEntity().getId());

        List<WordSpellingEntity> wordSpellingEntityList = learningWordsEntity.getWordsGroupEntity().getWordSpellingEntityList();
        //튜토리얼은 무조건 EN으로 세팅
        Optional<WordSpellingEntity> returnWordSpellingEntityOptional = wordSpellingEntityList.stream().filter(x -> x.getLanguageCode().equals("EN")).findFirst();

        model.addAttribute("learningWord", returnWordSpellingEntityOptional.isPresent() ? returnWordSpellingEntityOptional.get().getWordSpelling() : "");
        model.addAttribute("part", learningWordsEntity.getWordsGroupEntity().getPartsOfSpeech());

        return "page/guest-words-test";
    }



    /**
     * 학습중인 문제를 완전히 익혔을때 그 문제르 빼고 다음 문제 가져오기
     * @param model
     * @param wordUser
     * @return
     */
    @RequestMapping(value = "/next/exam-question", method = RequestMethod.POST)
    public String createNextWordsExamQuestion(Model model, @AuthenticationPrincipal WordUser wordUser) {

        billyWordsLearningService.createNextLearningWordsEntity(wordUser);

        return "redirect:/words-test";
    }
}
