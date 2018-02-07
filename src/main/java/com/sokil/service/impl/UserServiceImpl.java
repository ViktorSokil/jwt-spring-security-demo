package com.sokil.service.impl;


import com.sokil.entity.UserEntity;
import com.sokil.repository.UserRepository;
import com.sokil.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository userDAO;

    @Override
    public void saveUser(UserEntity user) {
        userDAO.save(user);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userDAO.findAll();
    }

    @Override
    public UserEntity findByUserName(String userName) {
        return userDAO.findByUserName(userName);
    }
}
