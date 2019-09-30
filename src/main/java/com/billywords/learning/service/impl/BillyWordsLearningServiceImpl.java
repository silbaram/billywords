package com.billywords.learning.service.impl;

import com.billywords.learning.repository.ExampleEntityRepository;
import com.billywords.learning.service.BillyWordsLearningService;
import com.billywords.user.models.UsersEntity;
import com.billywords.user.repository.UsersEntityRepository;
import com.billywords.words.models.ExampleEntity;
import com.billywords.words.models.LearningWordsEntity;
import com.billywords.words.models.WordSpellingEntity;
import com.billywords.words.repository.LearningWordsEntityRepository;
import com.billywords.words.repository.WordSpellingEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


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


    /**
     * 현재 학습 중인 문제를 가져온다.
     * @param isLearning
     * @return
     */
    @Override
    public LearningWordsEntity getLearningWordsEntity(Integer id, Boolean isLearning) {
        Optional<UsersEntity> byIdOptional = usersEntityRepository.findById(id);
        if(byIdOptional.isPresent()) {
            LearningWordsEntity learningWordsEntity = learningWordsEntityRepository.findByUsersEntityAndIsLearning(byIdOptional.get(), isLearning);

            return learningWordsEntity;
        } else {
            return null;
        }
    }



    /**
     * 사용자 학습 목록 중 보기를 만들어 준다.
     * @param id
     */
    @Override
    public List<ExampleEntity> createWordExample(Integer id) {
        int[] exampleNumber = new int[6];
        int isExampleMakeNumber = 0;

        // 보기 목록 뽑기
        Random random = new Random();
        while(true) {
            if(isExampleMakeNumber == 5) {
                break;
            }

            int check = random.nextInt(maxNumber);
            for(int makeCheck : exampleNumber) {
                if(makeCheck == check) {
                    check = -1;
                    break;
                }
            }

            if(check > 0) {
                exampleNumber[isExampleMakeNumber] = check;
                isExampleMakeNumber++;
            }
        }


        System.out.println("======================================");
        //보기 저장
        int orderNumber = 0;
        List<ExampleEntity> exampleEntityList = new ArrayList<>();
        for(int makeCheck : exampleNumber) {
            System.out.println(makeCheck);
            Optional<WordSpellingEntity> wordSpellingEntityByIdOptional = wordSpellingEntityRepository.findById(makeCheck);

            if(wordSpellingEntityByIdOptional.isPresent()) {
                ExampleEntity exampleEntity = new ExampleEntity();
                exampleEntity.setOrderNumber(orderNumber);
                exampleEntity.setWordSpellingEntity(wordSpellingEntityByIdOptional.get());
                exampleEntityList.add(exampleEntity);
                orderNumber++;
            }
        }

        exampleEntityRepository.saveAll(exampleEntityList);

        return exampleEntityList;
    }



    @Override
    public List<ExampleEntity> getExampleEntityList(String userEmail) {
        Random random = new Random();
        List<LearningWordsEntity> learningWordsEntityList = learningWordsEntityRepository.findByUsersEntity(usersEntityRepository.findByEmail(userEmail));

        //사용자가 학습중인 문제리스트가 없다면 만들어 준다.
        if(learningWordsEntityList.size() == 0) {

        }

        return learningWordsEntityList.get(random.nextInt(10)).getExampleEntityList();
    }
}
