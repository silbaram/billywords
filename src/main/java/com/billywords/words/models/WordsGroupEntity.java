package com.billywords.words.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "words_group", schema = "billywords")
public class WordsGroupEntity {
    private int id;
    private int importance;
    private String partsOfSpeech;
    private List<WordSpellingEntity> wordSpellingEntityList = new ArrayList<>();
    private List<LearningWordsEntity> learningWordsEntityList = new ArrayList<>();

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "importance")
    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    @Basic
    @Column(name = "parts_of_speech")
    public String getPartsOfSpeech() {
        return partsOfSpeech;
    }

    public void setPartsOfSpeech(String partsOfSpeech) {
        this.partsOfSpeech = partsOfSpeech;
    }

    @Basic
    @OneToMany(mappedBy = "wordsGroupEntity", fetch = FetchType.LAZY)
    public List<WordSpellingEntity> getWordSpellingEntityList() {
        return wordSpellingEntityList;
    }

    public void setWordSpellingEntityList(List<WordSpellingEntity> wordSpellingEntityList) {
        this.wordSpellingEntityList = wordSpellingEntityList;
    }

    @OneToMany(mappedBy = "wordsGroupEntity", fetch = FetchType.LAZY)
    public List<LearningWordsEntity> getLearningWordsEntityList() {
        return learningWordsEntityList;
    }

    public void setLearningWordsEntityList(List<LearningWordsEntity> learningWordsEntityList) {
        this.learningWordsEntityList = learningWordsEntityList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordsGroupEntity that = (WordsGroupEntity) o;
        return id == that.id &&
                importance == that.importance &&
                Objects.equals(partsOfSpeech, that.partsOfSpeech);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, importance, partsOfSpeech);
    }
}
