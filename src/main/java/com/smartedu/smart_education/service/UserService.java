package com.smartedu.smart_education.service;

import com.smartedu.smart_education.dto.response.UserResponse;
import com.smartedu.smart_education.entity.User;

import java.util.List;

public interface UserService {
    UserResponse getUserById(Long id);
    UserResponse getUserByEmail(String email);
    List<UserResponse> getUsersByRole(User.Role role);
    List<UserResponse> getActiveUsers();
    void deactivateUser(Long id);
    void activateUser(Long id);
}
