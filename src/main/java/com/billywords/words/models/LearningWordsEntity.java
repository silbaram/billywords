package com.billywords.words.models;

import com.billywords.user.models.UsersEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "learning_words", schema = "billywords")
public class LearningWordsEntity {
    private Integer id;
    private UsersEntity usersEntity;
    private WordsGroupEntity wordsGroupEntity;
    private Boolean isLearning;
    private Integer wrongCount;
    private Integer correctCount;
    private String hintCount;
    private List<ExampleEntity> exampleEntityList = new ArrayList<>();

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "users_id")
    public UsersEntity getUsersEntity() {
        return usersEntity;
    }

    public void setUsersEntity(UsersEntity usersEntity) {
        this.usersEntity = usersEntity;
    }

    @ManyToOne
    @JoinColumn(name = "words_id")
    public WordsGroupEntity getWordsGroupEntity() {
        return wordsGroupEntity;
    }

    public void setWordsGroupEntity(WordsGroupEntity wordsGroupEntity) {
        this.wordsGroupEntity = wordsGroupEntity;
    }

    @Basic
    @Column(name = "is_learning")
    public Boolean getIsLearning() {
        return isLearning;
    }

    public void setIsLearning(Boolean isLearning) {
        this.isLearning = isLearning;
    }

    @Basic
    @Column(name = "wrong_count")
    public Integer getWrongCount() {
        return wrongCount;
    }

    public void setWrongCount(Integer wrongCount) {
        this.wrongCount = wrongCount;
    }

    @Basic
    @Column(name = "correct_count")
    public Integer getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(Integer correctCount) {
        this.correctCount = correctCount;
    }

    @Basic
    @Column(name = "hint_count")
    public String getHintCount() {
        return hintCount;
    }

    public void setHintCount(String hintCount) {
        this.hintCount = hintCount;
    }

    @OneToMany(mappedBy = "learningWordsEntity")
    public List<ExampleEntity> getExampleEntityList() {
        return exampleEntityList;
    }

    public void setExampleEntityList(List<ExampleEntity> exampleEntityList) {
        this.exampleEntityList = exampleEntityList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LearningWordsEntity that = (LearningWordsEntity) o;
        return id == that.id &&
                usersEntity == that.usersEntity &&
                wordsGroupEntity == that.wordsGroupEntity &&
                Objects.equals(isLearning, that.isLearning) &&
                Objects.equals(wrongCount, that.wrongCount) &&
                Objects.equals(correctCount, that.correctCount) &&
                Objects.equals(hintCount, that.hintCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usersEntity, wordsGroupEntity, isLearning, wrongCount, correctCount, hintCount);
    }
}
