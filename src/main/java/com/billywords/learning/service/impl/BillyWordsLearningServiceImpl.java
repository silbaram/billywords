package com.billywords.learning.service.impl;

import com.billywords.cost.CommonCode;
import com.billywords.learning.repository.ExampleEntityRepository;
import com.billywords.learning.service.BillyWordsLearningService;
import com.billywords.learning.vo.WordsProblemVO;
import com.billywords.user.models.UsersEntity;
import com.billywords.user.repository.UsersEntityRepository;
import com.billywords.user.vo.WordUser;
import com.billywords.words.models.ExampleEntity;
import com.billywords.words.models.LearningWordsEntity;
import com.billywords.words.models.WordSpellingEntity;
import com.billywords.words.models.WordsGroupEntity;
import com.billywords.words.repository.LearningWordsEntityRepository;
import com.billywords.words.repository.WordSpellingEntityRepository;
import com.billywords.words.repository.WordsGroupEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;


@Service
public class BillyWordsLearningServiceImpl implements BillyWordsLearningService {

    @Value("${word.example.create.max}")
    private int maxNumber;

    @Autowired
    ExampleEntityRepository exampleEntityRepository;

    @Autowired
    LearningWordsEntityRepository learningWordsEntityRepository;

    @Autowired
    WordSpellingEntityRepository wordSpellingEntityRepository;

    @Autowired
    UsersEntityRepository usersEntityRepository;

    @Autowired
    WordsGroupEntityRepository wordsGroupEntityRepository;


    /**
     * 현재 학습 중인 문제를 가져온다.
     * @param isLearning
     * @return
     */
    @Override
    public LearningWordsEntity getLearningWordsEntity(Integer id, Boolean isLearning) {
        Optional<UsersEntity> usersEntityOptional = usersEntityRepository.findById(id);
        return usersEntityOptional.map(usersEntity -> learningWordsEntityRepository.findByUsersEntityAndIsLearning(usersEntity, isLearning)).orElse(null);
    }


    /**
     * 사용자 학습 문제의 보기를 만들어 준다.
     * @param id
     * @param learningWordsEntity
     */
    @Override
    public List<ExampleEntity> createWordExample(Integer id, LearningWordsEntity learningWordsEntity) {
        final List<ExampleEntity> exampleEntityList = new ArrayList<>();

        //TODO 학습을 하기 위한 언어를 선택 하고 가져와서 문제를 어떤 언어로 출제 할지 선택 하는 부분이 필요
        Optional<WordSpellingEntity> spellingEntityOptional = learningWordsEntity.getWordsGroupEntity().getWordSpellingEntityList().stream().filter(x -> x.getLanguageCode().equals("EN")).findFirst();
        int spellingEntityNumber = spellingEntityOptional.isPresent() ? spellingEntityOptional.get().getId() : 1;

        //유저 정보를 찾는다
        final Optional<UsersEntity> usersEntityOptional = usersEntityRepository.findById(id);
        if(usersEntityOptional.isPresent()) {

            int[] exampleNumber = new int[6];
            int isExampleMakeNumber = 0;

            // 보기 목록 뽑기
            Random random = new Random();
            while(true) {
                if(isExampleMakeNumber == 5) {
                    break;
                }

                int check = random.nextInt(maxNumber);
                if(spellingEntityNumber == check) {
                    check = -1;
                } else {

                    for(int makeCheck : exampleNumber) {
                        if(check == 0 || makeCheck == check) {
                            check = -1;
                            break;
                        }
                    }
                }


                if(check > 0) {
                    exampleNumber[isExampleMakeNumber] = check;
                    isExampleMakeNumber++;
                }
            }

            //정답 보기를 랜덤으로 자리 잡아 준다
            int changeExample =  random.nextInt(5);
            exampleNumber[5] = exampleNumber[changeExample];
            exampleNumber[changeExample] = spellingEntityNumber;

            //보기 저장
            int orderNumber = 0;
            for(int makeCheck : exampleNumber) {
                //TODO 언어코드 부분을 저장하고 가져오는 부분이 만들어져야됨
                WordSpellingEntity wordSpellingEntity = wordSpellingEntityRepository.findByWordsGroupEntityAndLanguageCode(wordsGroupEntityRepository.findById(makeCheck), "KO");

                ExampleEntity exampleEntity = new ExampleEntity();
                exampleEntity.setLearningWordsEntity(learningWordsEntity);
                exampleEntity.setOrderNumber(orderNumber);
                exampleEntity.setWordSpellingEntity(wordSpellingEntity);
                exampleEntityList.add(exampleEntity);
                orderNumber++;

            }

            exampleEntityRepository.saveAll(exampleEntityList);
        }

        return exampleEntityList;
    }


    /**
     * 현재 학습중인 단어를 가져와서 보기에서 센택한 값과 배교
     * @param id
     * @param wordsProblem
     */
    @Override
    public boolean isWordQuestionCorrect(Integer id, WordsProblemVO wordsProblem) {

        LearningWordsEntity learningWordsEntity = getLearningWordsEntity(id, true);

        if(wordsProblem.getChooseExampleId().equals(String.valueOf(learningWordsEntity.getWordsGroupEntity().getId()))) {
            wordsProblem.setStatus(CommonCode.WORD_PROBLEM.SUCCESS);
            learningWordsEntity.setCorrectCount(learningWordsEntity.getCorrectCount() + 1);
        } else {
            wordsProblem.setStatus(CommonCode.WORD_PROBLEM.FAIL);
            learningWordsEntity.setWrongCount(learningWordsEntity.getWrongCount() + 1);
        }

        LearningWordsEntity reloadLearningWordsEntity = learningWordsEntityRepository.save(learningWordsEntity);

        return reloadLearningWordsEntity.getCorrectCount() > reloadLearningWordsEntity.getWrongCount();
    }


    /**
     * 다음 문제를 출재 하기 위해 다음문제 선택
     * @param wordUser
     */
    @Override
    public LearningWordsEntity nextLearningWordsEntity(WordUser wordUser) {

        final List<LearningWordsEntity> saveLearningWordsEntityList = new ArrayList<>();

        // 학습중인 단어
        LearningWordsEntity learningWordsEntity = getLearningWordsEntity(wordUser.getUserId(), true);

        final Optional<UsersEntity> usersEntityOptional = usersEntityRepository.findById(wordUser.getUserId());

        if(usersEntityOptional.isPresent()) {

            // 기존 문제의 보기는 삭제
            exampleEntityRepository.deleteAll(learningWordsEntity.getExampleEntityList());

            List<LearningWordsEntity> learningWordsEntityList = usersEntityOptional.get().getLearningWordsEntityList();

            Random random = new Random();
            while (true) {
                int isLearning = random.nextInt(learningWordsEntityList.size());
                LearningWordsEntity nextLearningWordsEntity = learningWordsEntityList.get(isLearning);
                if(learningWordsEntity.getWordsGroupEntity().getImportance() != nextLearningWordsEntity.getWordsGroupEntity().getImportance()) {
                    learningWordsEntity.setIsLearning(false);
                    learningWordsEntity.setExampleEntityList(null);
                    saveLearningWordsEntityList.add(learningWordsEntity);
                    nextLearningWordsEntity.setIsLearning(true);
                    saveLearningWordsEntityList.add(nextLearningWordsEntity);
                    break;
                }
            }

            learningWordsEntityRepository.saveAll(saveLearningWordsEntityList);
        }

        return saveLearningWordsEntityList.stream().filter(LearningWordsEntity::getIsLearning).findFirst().orElse(null);
    }


    // 현재 학습한 단어를 완료하고 다음 문제랑 교체
    public void createNextLearningWordsEntity(WordUser wordUser) {

        // 학습중인 단어
        LearningWordsEntity learningWordsEntity = getLearningWordsEntity(wordUser.getUserId(), true);

        final Optional<UsersEntity> usersEntityOptional = usersEntityRepository.findById(wordUser.getUserId());
        if(usersEntityOptional.isPresent()) {
            List<WordsGroupEntity> wordsGroupEntityList = usersEntityOptional.get().getLearningWordsEntityList().stream().map(LearningWordsEntity::getWordsGroupEntity).collect(Collectors.toList());

            int maxId = wordsGroupEntityList.stream().map(WordsGroupEntity::getImportance).max(Integer::compare).orElse(0);

            WordsGroupEntity wordsGroupEntity = wordsGroupEntityRepository.findByImportance(maxId + 1);
            LearningWordsEntity saveLearningWordsEntity = new LearningWordsEntity();
            saveLearningWordsEntity.setUsersEntity(usersEntityOptional.get());
            saveLearningWordsEntity.setWordsGroupEntity(wordsGroupEntity);
            saveLearningWordsEntity.setWrongCount(0);
            saveLearningWordsEntity.setCorrectCount(0);
            saveLearningWordsEntity.setHintCount("0");
            saveLearningWordsEntity.setIsLearning(true);

            learningWordsEntityRepository.save(saveLearningWordsEntity);
        }

        learningWordsEntityRepository.delete(learningWordsEntity);
    }
}
