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

    @Value("${word.learning.ttl.rul}")
    private String ttlUrl;

    private static String guestLearningWord = "EN";


    /**
     * 단어 테스트 페이지
     * @param model
     * @param wordUser
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String wordsTest(Model model, @AuthenticationPrincipal WordUser wordUser){

        if(wordUser == null) {
            return "redirect:/login";
        }
        //학습중인 단어
        LearningWordsEntity learningWordsEntity = billyWordsLearningService.getLearningWordsEntity(wordUser.getUserId(), true);

        if(learningWordsEntity.getExampleEntityList().size() == 0) {
            //학습을 위한 문제의 보기를 만든다
            List<ExampleEntity> exampleEntityList = billyWordsLearningService.createWordExample(wordUser.getUserId(), wordUser.getFromLanguage(), wordUser.getToLanguage(), learningWordsEntity);

            model.addAttribute("learningWordsExampleList", exampleEntityList);
        } else {
            model.addAttribute("learningWordsExampleList", learningWordsEntity.getExampleEntityList());
        }

        List<WordSpellingEntity> wordSpellingEntityList = learningWordsEntity.getWordsGroupEntity().getWordSpellingEntityList();
        Optional<WordSpellingEntity> returnWordSpellingEntityOptional = wordSpellingEntityList.stream().filter(x -> x.getLanguageCode().equals(wordUser.getToLanguage())).findFirst();

        String learningWord = returnWordSpellingEntityOptional.isPresent() ? returnWordSpellingEntityOptional.get().getWordSpelling() : "";
        model.addAttribute("learningWord", learningWord);
        model.addAttribute("part", learningWordsEntity.getWordsGroupEntity().getPartsOfSpeech());
        model.addAttribute("ttlUrl", ttlUrl.replace("#WORD#", learningWord));

        return "page/words-test";
    }



    /**
     * 게스트 단어 테스트 페이지
     * @param model
     * @return
     */
    @RequestMapping(value = "/guest", method = RequestMethod.GET)
    public String guestWordsTest(@RequestParam String prefecture, Model model){

        //사용자 정보가 없다면 비회면 로직으로 보여준다.
        List<LearningWordsEntity> learningWordsEntityList= billyWordsLearningService.getGuestLearningWordsEntityList();

        //학습중인 단어
        LearningWordsEntity learningWordsEntity = learningWordsEntityList.get(0);

        //튜토리얼 학습을 위한 문제의 임시보기를 만든다
        List<ExampleEntity> exampleEntityList = billyWordsLearningService.createGuestWordExample(learningWordsEntity, prefecture);
        model.addAttribute("learningWordsExampleList", exampleEntityList);
        model.addAttribute("learningWordsPosition", 1);
        model.addAttribute("learningWordsGroupEntityId", learningWordsEntity.getWordsGroupEntity().getId());

        //튜토리얼 문제는 처음에 선택한 언어로
        List<WordSpellingEntity> wordSpellingEntityList = learningWordsEntity.getWordsGroupEntity().getWordSpellingEntityList();
        Optional<WordSpellingEntity> returnWordSpellingEntityOptional = wordSpellingEntityList.stream().filter(x -> x.getLanguageCode().equals(guestLearningWord)).findFirst();

        String learningWord = returnWordSpellingEntityOptional.isPresent() ? returnWordSpellingEntityOptional.get().getWordSpelling() : "";
        model.addAttribute("learningWord", learningWord);
        model.addAttribute("part", learningWordsEntity.getWordsGroupEntity().getPartsOfSpeech());
        model.addAttribute("prefecture", prefecture);
        model.addAttribute("ttlUrl", ttlUrl.replace("#WORD#", learningWord));

        return "page/guest-words-test";
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
    public String guestWordsNextExample(@RequestParam String prefecture, @RequestParam String learningWordsPosition, Model model) {
        List<LearningWordsEntity> learningWordsEntityList = billyWordsLearningService.getGuestLearningWordsEntityList();

        LearningWordsEntity learningWordsEntity = learningWordsEntityList.get(Integer.valueOf(learningWordsPosition.trim()));

        //튜토리얼 학습을 위한 문제의 임시보기를 만든다
        List<ExampleEntity> exampleEntityList = billyWordsLearningService.createGuestWordExample(learningWordsEntity, prefecture);
        model.addAttribute("learningWordsExampleList", exampleEntityList);
        model.addAttribute("learningWordsEntityList", learningWordsEntityList.toArray());
        model.addAttribute("learningWordsPosition", Integer.valueOf(learningWordsPosition.trim()) + 1);
        model.addAttribute("learningWordsGroupEntityId", learningWordsEntity.getWordsGroupEntity().getId());
        model.addAttribute("learningWordsEntityTotalCount", learningWordsEntityList.size());

        List<WordSpellingEntity> wordSpellingEntityList = learningWordsEntity.getWordsGroupEntity().getWordSpellingEntityList();
        //튜토리얼은 문제는 처음에 선택한 언어로
        Optional<WordSpellingEntity> returnWordSpellingEntityOptional = wordSpellingEntityList.stream().filter(x -> x.getLanguageCode().equals(guestLearningWord)).findFirst();

        model.addAttribute("learningWord", returnWordSpellingEntityOptional.isPresent() ? returnWordSpellingEntityOptional.get().getWordSpelling() : "");
        model.addAttribute("part", learningWordsEntity.getWordsGroupEntity().getPartsOfSpeech());
        model.addAttribute("prefecture", prefecture);

        return "page/guest-words-test";
    }


    /**
     * 사용자가 학습중인 단여를 서로 바꿀때 (2차 진행)
     * 학습을 완료 하면 다음단계로 모국어랑 영어랑 바꾸어서 처음주터 진행 되도록 << 이직 이부분은 진행단계
     * @param wordUser
     * @return
     */
    @RequestMapping(value = "/change-learning-word")
    public String changeLearningWord(@AuthenticationPrincipal WordUser wordUser) {

        if(wordUser == null) {
            return "redirect:/words-test";
        }

        billyWordsLearningService.changeLearningWord(wordUser);

        return "redirect:/words-test";
    }



    /**
     * 학습중인 문제를 완전히 익혔을때 그 문제르 빼고 다음 문제 가져오기
     * @param model
     * @param wordUser
     * @return
     */
    @RequestMapping(value = "/next/exam-question", method = RequestMethod.POST)
    public String createNextWordsExamQuestion(Model model, @AuthenticationPrincipal WordUser wordUser) {

        if(billyWordsLearningService.createNextLearningWordsEntity(wordUser)) {
            return "redirect:/finished";
        } else {
            return "redirect:/words-test";
        }
    }


    /**
     * 모든 문제를 다 풀었을때 축하 페이지
     * @param wordUser
     * @return
     */
    @RequestMapping(value = "/finished", method = RequestMethod.GET)
    public String billyFinished(@AuthenticationPrincipal WordUser wordUser) {
        return "page/words-finished";
    }
}
