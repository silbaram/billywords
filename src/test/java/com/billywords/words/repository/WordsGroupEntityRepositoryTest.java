package com.billywords.words.repository;

import com.billywords.words.models.WordsGroupEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class WordsGroupEntityRepositoryTest {

    @Autowired
    WordsGroupEntityRepository wordsGroupEntityRepository;



    @Test
    public void 회원가입시_단어초기_그룹찾기() {
        List<WordsGroupEntity> wordsGroupEntityList = wordsGroupEntityRepository.findByImportanceBetween(1, 9);

        for(WordsGroupEntity wordsGroupEntity : wordsGroupEntityList) {
            System.out.println("getId : " + wordsGroupEntity.getId());
            System.out.println("getImportance : " + wordsGroupEntity.getImportance());
            System.out.println("getPartsOfSpeech : " + wordsGroupEntity.getPartsOfSpeech());
        }
    }

}
