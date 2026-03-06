package com.smartedu.smart_education.service;

import com.smartedu.smart_education.entity.User;

import java.util.List;

public interface UserService {
    User registerUser(User user);

    User getUserById(Long id);

    User getUserByEmail(String email);
    List<User> getAllUsersByRole(User.Role role);
    List<User> getActiveUsers();
    void deactivateUserById(Long id);
    void activateUserById(Long id);
}
