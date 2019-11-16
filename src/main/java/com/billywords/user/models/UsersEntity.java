package com.billywords.user.models;

import com.billywords.security.models.AuthorityEntity;
import com.billywords.words.models.LearningWordsEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "billywords")
public class UsersEntity {
    private Integer id;
    private String email;
    private String password;
    private String fromLanguage;
    private String toLanguage;
    private String name;
    private Timestamp createDate;
    private Timestamp updateDate;
    private Integer isAccountNonExpired;
    private Integer isAccountNonLocked;
    private Integer isCredentialsNonExpired;
    private Integer isEnabled;
    private List<AuthorityEntity> authorityEntityList = new ArrayList<>();
    private List<LearningWordsEntity> learningWordsEntityList = new ArrayList<>();

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
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "from_language")
    public String getFromLanguage() {
        return fromLanguage;
    }

    public void setFromLanguage(String fromLanguage) {
        this.fromLanguage = fromLanguage;
    }

    @Basic
    @Column(name = "to_language")
    public String getToLanguage() {
        return toLanguage;
    }

    public void setToLanguage(String toLanguage) {
        this.toLanguage = toLanguage;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "create_date")
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "update_date")
    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    @Basic
    @Column(name = "is_account_non_expired")
    public Integer getIsAccountNonExpired() {
        return isAccountNonExpired;
    }

    public void setIsAccountNonExpired(Integer isAccountNonExpired) {
        this.isAccountNonExpired = isAccountNonExpired;
    }

    @Basic
    @Column(name = "is_account_non_locked")
    public Integer getIsAccountNonLocked() {
        return isAccountNonLocked;
    }

    public void setIsAccountNonLocked(Integer isAccountNonLocked) {
        this.isAccountNonLocked = isAccountNonLocked;
    }

    @Basic
    @Column(name = "is_credentials_non_expired")
    public Integer getIsCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    public void setIsCredentialsNonExpired(Integer isCredentialsNonExpired) {
        this.isCredentialsNonExpired = isCredentialsNonExpired;
    }

    @Basic
    @Column(name = "is_enabled")
    public Integer getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }

    @OneToMany(mappedBy = "usersEntity", fetch = FetchType.EAGER)
    public List<AuthorityEntity> getAuthorityEntityList() {
        return authorityEntityList;
    }

    public void setAuthorityEntityList(List<AuthorityEntity> authorityEntityList) {
        this.authorityEntityList = authorityEntityList;
    }

    @OneToMany(mappedBy = "usersEntity", fetch = FetchType.LAZY)
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
        UsersEntity that = (UsersEntity) o;
        return id == that.id &&
                Objects.equals(email, that.email) &&
                Objects.equals(password, that.password) &&
                Objects.equals(fromLanguage, that.fromLanguage) &&
                Objects.equals(toLanguage, that.toLanguage) &&
                Objects.equals(name, that.name) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(updateDate, that.updateDate) &&
                Objects.equals(isAccountNonExpired, that.isAccountNonExpired) &&
                Objects.equals(isAccountNonLocked, that.isAccountNonLocked) &&
                Objects.equals(isCredentialsNonExpired, that.isCredentialsNonExpired) &&
                Objects.equals(isEnabled, that.isEnabled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, fromLanguage, toLanguage, name, createDate, updateDate, isAccountNonExpired, isAccountNonLocked, isCredentialsNonExpired, isEnabled);
    }
}
