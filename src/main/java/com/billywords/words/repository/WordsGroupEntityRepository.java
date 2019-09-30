package com.billywords.words.repository;

import com.billywords.words.models.WordsGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WordsGroupEntityRepository extends JpaRepository<WordsGroupEntity, Integer> {

    @Query("SELECT distinct g FROM WordsGroupEntity g JOIN FETCH g.wordSpellingEntityList WHERE g.importance >= ?1 and g.importance <= ?2" )
    List<WordsGroupEntity> findByImportanceBetween(int fromImportance, int toImportance);

    WordsGroupEntity findByImportance(int importance);
}
