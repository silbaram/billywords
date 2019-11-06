package com.billywords.words.repository;

import com.billywords.words.models.WordSpellingEntity;
import com.billywords.words.models.WordsGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WordSpellingEntityRepository extends JpaRepository<WordSpellingEntity, Integer> {
    public WordSpellingEntity findByWordsGroupEntityAndLanguageCode(WordsGroupEntity wordsGroupEntity, String languageCode);
}
