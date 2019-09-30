package com.billywords.words.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "example", schema = "billywords")
public class ExampleEntity {
    private Integer id;
    private Integer orderNumber;
    private LearningWordsEntity learningWordsEntity;
    private WordSpellingEntity wordSpellingEntity;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "order_number")
    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    @ManyToOne
    @JoinColumn(name = "learning_words_id")
    public LearningWordsEntity getLearningWordsEntity() {
        return learningWordsEntity;
    }

    public void setLearningWordsEntity(LearningWordsEntity learningWordsEntity) {
        this.learningWordsEntity = learningWordsEntity;
    }

    @OneToOne
    @JoinColumn(name = "word_spelling_id")
    public WordSpellingEntity getWordSpellingEntity() {
        return wordSpellingEntity;
    }

    public void setWordSpellingEntity(WordSpellingEntity wordSpellingEntity) {
        this.wordSpellingEntity = wordSpellingEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExampleEntity that = (ExampleEntity) o;
        return id == that.id &&
                Objects.equals(orderNumber, that.orderNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderNumber);
    }
}
