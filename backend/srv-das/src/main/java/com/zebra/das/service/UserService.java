
package com.zebra.das.service;

import com.zebra.das.model.api.User;
import java.util.List;

public interface UserService {
    public List<User> findAll();

    public User find(String userId);
}
