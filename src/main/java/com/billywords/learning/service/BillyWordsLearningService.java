package com.billywords.learning.service;

import com.billywords.words.models.ExampleEntity;
import com.billywords.words.models.LearningWordsEntity;

import java.util.List;

public interface BillyWordsLearningService {
    public List<ExampleEntity> createWordExample(Integer id);

    public LearningWordsEntity getLearningWordsEntity(Integer userEmail, Boolean isLearning);

    public List<ExampleEntity> getExampleEntityList(String userEmail);
}
