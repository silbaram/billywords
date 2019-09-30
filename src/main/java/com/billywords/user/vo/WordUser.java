package com.billywords.user.vo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Set;



public class WordUser extends User {
    private Integer userId;


    public WordUser(Integer userId, String email, String password, Set<GrantedAuthority> grantedAuthorities) {
        super(email, password, grantedAuthorities);
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
