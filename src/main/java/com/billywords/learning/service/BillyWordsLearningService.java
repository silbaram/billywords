package com.billywords.learning.service;

import com.billywords.learning.vo.WordsProblemVO;
import com.billywords.user.vo.WordUser;
import com.billywords.words.models.ExampleEntity;
import com.billywords.words.models.LearningWordsEntity;

import java.util.List;

public interface BillyWordsLearningService {
    public List<ExampleEntity> createWordExample(Integer id, LearningWordsEntity learningWordsEntity);

    public LearningWordsEntity getLearningWordsEntity(Integer userEmail, Boolean isLearning);

    public boolean isWordQuestionCorrect(Integer id, WordsProblemVO wordsProblem);

    public LearningWordsEntity nextLearningWordsEntity(WordUser wordUser);

    public void createNextLearningWordsEntity(WordUser wordUser);
}
