package com.billywords.learning;



import com.billywords.learning.service.impl.BillyWordsLearningServiceImpl;
import com.billywords.user.repository.UsersEntityRepository;
import com.billywords.words.models.ExampleEntity;
import com.billywords.words.models.LearningWordsEntity;
import com.billywords.words.repository.LearningWordsEntityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@Rollback(value=true)
@RunWith(SpringRunner.class)
@SpringBootTest
public class BillyWordsTestServiceTest {

    @Autowired
    BillyWordsLearningServiceImpl billyWordsLearningService;

    @Autowired
    LearningWordsEntityRepository learningWordsEntityRepository;



//    @Test
    public void 문제만들기테스트() {
//        billyWordsLearningService.createWordExample("4");

//        List<ExampleEntity> exampleEntityList = billyWordsLearningService.getExampleEntityList("aaa@aaa.com");
//        System.out.println(exampleEntityList.toString());
    }

}
