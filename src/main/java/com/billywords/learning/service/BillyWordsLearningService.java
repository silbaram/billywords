package com.billywords.learning.service;

import com.billywords.learning.vo.WordsProblemVO;
import com.billywords.user.vo.WordUser;
import com.billywords.words.models.ExampleEntity;
import com.billywords.words.models.LearningWordsEntity;

import java.util.List;

public interface BillyWordsLearningService {
    public List<ExampleEntity> createWordExample(Integer id, String fromLanguage, String toLanguage, LearningWordsEntity learningWordsEntity);
    public List<ExampleEntity> createGuestWordExample(LearningWordsEntity learningWordsEntity, String prefecture);

    public LearningWordsEntity getLearningWordsEntity(Integer id, Boolean isLearning);
    public List<LearningWordsEntity> getGuestLearningWordsEntityList();

    public boolean isWordQuestionCorrect(Integer id, WordsProblemVO wordsProblem);
    public boolean isGuestWordQuestionCorrect(WordsProblemVO wordsProblem);

    public LearningWordsEntity nextLearningWordsEntity(WordUser wordUser);

    public boolean createNextLearningWordsEntity(WordUser wordUser);

    public int getLearningWordsPosition(Integer id);

    public void changeLearningWord(WordUser wordUser);
}
