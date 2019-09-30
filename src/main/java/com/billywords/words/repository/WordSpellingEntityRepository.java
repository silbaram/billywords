package com.billywords.words.repository;

import com.billywords.words.models.WordSpellingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordSpellingEntityRepository extends JpaRepository<WordSpellingEntity, Integer> {
}
