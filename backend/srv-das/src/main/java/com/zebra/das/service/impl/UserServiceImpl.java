
package com.zebra.das.service.impl;

import com.zebra.das.model.api.User;
import com.zebra.das.repository.UserRepository;
import com.zebra.das.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository repository;
    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User find(String userId) {
        return repository.findOne(userId);
    }

}
