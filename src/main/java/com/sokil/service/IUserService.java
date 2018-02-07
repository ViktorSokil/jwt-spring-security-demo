package com.sokil.service;


import com.sokil.entity.UserEntity;

import java.util.List;

public interface IUserService {
    void saveUser(UserEntity user);
    List<UserEntity> getAllUsers();
    UserEntity findByUserName(String userName);
}
