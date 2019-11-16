package com.billywords.user.service.impl;

import com.billywords.security.models.AuthorityEntity;
import com.billywords.security.repository.AuthorityEntityRepository;
import com.billywords.user.models.UsersEntity;
import com.billywords.user.repository.UsersEntityRepository;
import com.billywords.user.service.UserService;
import com.billywords.user.vo.UserVO;
import com.billywords.words.models.LearningWordsEntity;
import com.billywords.words.models.WordsGroupEntity;
import com.billywords.words.repository.LearningWordsEntityRepository;
import com.billywords.words.repository.WordsGroupEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersEntityRepository usersEntityRepository;

    @Autowired
    private AuthorityEntityRepository authorityEntityRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private WordsGroupEntityRepository wordsGroupEntityRepository;

    @Autowired
    private LearningWordsEntityRepository learningWordsEntityRepository;

    @Value("${word.learning.create.max}")
    private int maxNumber;



    @Override
    @Transactional
    public void saveUser(UserVO user, String roles) {

        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        Calendar cal = Calendar.getInstance();
        String today = formatter.format(cal.getTime());
        Timestamp ts = Timestamp.valueOf(today);

        // 사용자 기본 정보 등록
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setEmail(user.getEmail());
        usersEntity.setName(user.getName());
        usersEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        usersEntity.setFromLanguage(user.getFromLanguage().toUpperCase());
        usersEntity.setToLanguage("EN"); //학습 언어는 기본으로 EN
        usersEntity.setCreateDate(ts);
        usersEntityRepository.save(usersEntity);

        // 사용자 권한 저장
        List<AuthorityEntity> authorityEntityList = new ArrayList<>();
        AuthorityEntity authorityEntity = new AuthorityEntity();
        authorityEntity.setUsersEntity(usersEntity);
        authorityEntity.setAuthorityName(roles);
        authorityEntityList.add(authorityEntity);
        authorityEntityRepository.save(authorityEntity);

        usersEntity.setAuthorityEntityList(authorityEntityList);

        //첫 문제 목록 등록
        List<WordsGroupEntity> wordsGroupEntityList = wordsGroupEntityRepository.findByImportanceBetween(1, maxNumber); //TODO 문제를 가져오는 범위를 10으로 변경해야됨
        Random random = new Random();
        int isLearning = random.nextInt(wordsGroupEntityList.size()) + 1;
        int isLearningCheckNumber = 0;
        List<LearningWordsEntity> learningWordsEntityTempList = new ArrayList<>();

        for(WordsGroupEntity wordsGroupEntity : wordsGroupEntityList) {
            LearningWordsEntity learningWordsEntity = new LearningWordsEntity();
            learningWordsEntity.setUsersEntity(usersEntity);
            learningWordsEntity.setWordsGroupEntity(wordsGroupEntity);
            learningWordsEntity.setWrongCount(0);
            learningWordsEntity.setCorrectCount(0);
            learningWordsEntity.setHintCount("0");
            if(isLearning == isLearningCheckNumber) {
                learningWordsEntity.setIsLearning(true);
            } else {
                learningWordsEntity.setIsLearning(false);
            }
            learningWordsEntityTempList.add(learningWordsEntity);
            isLearningCheckNumber++;
        }
        learningWordsEntityRepository.saveAll(learningWordsEntityTempList);

        usersEntityRepository.save(usersEntity);
    }

    @Override
    public UsersEntity findByEmail(String email) {
        return usersEntityRepository.findByEmail(email);
    }

    @Override
    public Map<String, String> emailUniqueCheck(String email) {

        Optional<UsersEntity> byEmailOptional = Optional.ofNullable(usersEntityRepository.findByEmail(email));
        Map<String, String> returnValue = new HashMap<>();

        if(byEmailOptional.isPresent()) {
            returnValue.put("STATUS", "TRUE");
        } else {
            returnValue.put("STATUS", "FALSE");
        }

        return returnValue;
    }
}
