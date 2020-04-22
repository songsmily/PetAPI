package com.songsmily.petapi.service;

import com.songsmily.petapi.entity.User;

public interface AuthService {

    User GithubAuth(String code, String state);
    User  QQAuth(String code, String state);

}
