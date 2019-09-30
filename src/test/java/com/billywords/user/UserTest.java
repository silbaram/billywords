package com.billywords.user;

import com.billywords.user.enums.UserType;
import com.billywords.user.models.UsersEntity;
import com.billywords.user.service.UserService;
import com.billywords.user.vo.UserVO;
import com.billywords.words.models.WordSpellingEntity;
import com.billywords.words.models.WordsGroupEntity;
import com.billywords.words.repository.WordSpellingEntityRepository;
import com.billywords.words.repository.WordsGroupEntityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;


@Rollback(value=true)
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
    UserService userService;

    @Autowired
    WordsGroupEntityRepository wordsGroupEntityRepository;

    @Autowired
    WordSpellingEntityRepository wordSpellingEntityRepository;


    @Test
//    @Transactional
    public void 회원가입테스트() {
        UserVO userForm = new UserVO();
        userForm.setEmail("aaa1@aaa.com");
        userForm.setLanguage("KO");
        userForm.setName("테스트");
        userForm.setPassword("1234");
        userService.saveUser(userForm, UserType.USER.toString());

        UsersEntity usersEntity = userService.findByEmail("aaa1@aaa.com");

        System.out.println("usersEntity.getEmail() : " + usersEntity.getEmail());
        System.out.println(usersEntity.getAuthorityEntityList().get(0).getId());

        assertEquals("aaa1@aaa.com", usersEntity.getEmail());
    }



    @Test
    public void 회원로그인테스트() {
        UsersEntity usersEntity = userService.findByEmail("aaa@aaa.com");

        System.out.println("usersEntity.getEmail() : " + usersEntity.getEmail());
        System.out.println(usersEntity.getAuthorityEntityList().get(0).getId());

        assertEquals("aaa@aaa.com", usersEntity.getEmail());
    }


    @Test
    public void 회원가입시처음문제리스트() {

        List<WordsGroupEntity> wordsGroupEntityList = wordsGroupEntityRepository.findByImportanceBetween(1, 2);

        for(WordsGroupEntity wordsGroupEntity : wordsGroupEntityList) {

//            System.out.println("getId : " + wordsGroupEntity.getId());
//            System.out.println("getImportance : " + wordsGroupEntity.getImportance());
//            System.out.println("getPartsOfSpeech : " + wordsGroupEntity.getPartsOfSpeech());

//            List<WordSpellingEntity> wordSpellingEntityList = wordsGroupEntity.getWordSpellingEntityList();
//            System.out.println("1. :" + wordSpellingEntityList.size());

//            List<WordSpellingEntity> wordSpellingEntityList = wordsGroupEntity.getWordSpellingEntityList();
//            System.out.println(wordSpellingEntityList.get(0));
//            System.out.println("wordSpellingEntityList : " + wordSpellingEntityList);

//            Optional<WordSpellingEntity> wordSpellingEntityOptional = wordSpellingEntityRepository.findById(wordsGroupEntity.getId());
//            System.out.println(wordSpellingEntityOptional.get().getWordSpelling());
        }



//        assertEquals(2, wordsGroupEntityList.size());
    }
}
