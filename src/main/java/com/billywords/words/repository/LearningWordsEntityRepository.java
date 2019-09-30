package com.billywords.words.repository;

import com.billywords.user.models.UsersEntity;
import com.billywords.words.models.LearningWordsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LearningWordsEntityRepository extends JpaRepository<LearningWordsEntity, Integer> {
    public List<LearningWordsEntity> findByUsersEntity(UsersEntity usersEntity);
    public LearningWordsEntity findByUsersEntityAndIsLearning(UsersEntity usersEntity, Boolean isLearning);
}
