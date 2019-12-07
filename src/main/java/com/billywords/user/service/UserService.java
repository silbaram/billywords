package com.billywords.user.service;

import com.billywords.user.vo.UserVO;

import java.util.Map;

public interface UserService {
    void saveUser(UserVO user, String roles);
    Map<String, String> emailUniqueCheck(String email);
}
