package com.billywords.words.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "word_spelling", schema = "billywords")
public class WordSpellingEntity {
    private Integer id;
    private String languageCode;
    private String wordSpelling;
    private WordsGroupEntity wordsGroupEntity;
//    private ExampleEntity exampleEntity;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "language_code")
    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    @Basic
    @Column(name = "word_spelling")
    public String getWordSpelling() {
        return wordSpelling;
    }

    public void setWordSpelling(String wordSpelling) {
        this.wordSpelling = wordSpelling;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "words_group_id")
    public WordsGroupEntity getWordsGroupEntity() {
        return wordsGroupEntity;
    }

    public void setWordsGroupEntity(WordsGroupEntity wordsGroupEntity) {
        this.wordsGroupEntity = wordsGroupEntity;
    }

//    @OneToOne(mappedBy = "wordSpellingEntity")
//    public ExampleEntity getExampleEntity() {
//        return exampleEntity;
//    }
//
//    public void setExampleEntity(ExampleEntity exampleEntity) {
//        this.exampleEntity = exampleEntity;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordSpellingEntity that = (WordSpellingEntity) o;
        return id == that.id &&
                Objects.equals(languageCode, that.languageCode) &&
                Objects.equals(wordSpelling, that.wordSpelling);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, languageCode, wordSpelling);
    }
}
