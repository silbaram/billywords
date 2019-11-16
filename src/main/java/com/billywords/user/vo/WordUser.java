package com.billywords.user.vo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Set;



public class WordUser extends User {
    private Integer userId;
    private String fromLanguage;
    private String toLanguage;


    public WordUser(Integer userId, String fromLanguage, String toLanguage, String email, String password, Set<GrantedAuthority> grantedAuthorities) {
        super(email, password, grantedAuthorities);
        this.userId = userId;
        this.fromLanguage = fromLanguage;
        this.toLanguage = toLanguage;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFromLanguage() {
        return fromLanguage;
    }

    public void setFromLanguage(String fromLanguage) {
        this.fromLanguage = fromLanguage;
    }

    public String getToLanguage() {
        return toLanguage;
    }

    public void setToLanguage(String toLanguage) {
        this.toLanguage = toLanguage;
    }
}
