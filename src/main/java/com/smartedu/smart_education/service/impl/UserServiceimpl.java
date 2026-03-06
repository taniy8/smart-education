package com.smartedu.smart_education.service.impl;

import com.smartedu.smart_education.entity.User;
import com.smartedu.smart_education.repository.UserRepository;
import com.smartedu.smart_education.service.UserService;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserServiceimpl implements UserService {

    @Autowired
    private UserRepository userRepo;
    @Override
    public User registerUser(User user) {
      if(userRepo.existsByEmail(user.getEmail())) {
          throw new RuntimeException("Email already registered: " + user.getEmail());
      }
      return userRepo.save(user);
    }

    @Override
    public User getUserById(Long id) {
      return userRepo.findById(id)
              .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    @Override
    public List<User> getAllUsersByRole(User.Role role) {
        return userRepo.findByRole(role);
    }

    @Override
    public List<User> getActiveUsers() {
        return userRepo.findByisActiveTrue();
    }

    @Override
    public void deactivateUserById(Long id) {
        User user = getUserById(id);
       user.setIsActive(false);
    }

    @Override
    public void activateUserById(Long id) {
        User user = getUserById(id);
        user.setIsActive(true);
        userRepo.save(user);
    }
}
