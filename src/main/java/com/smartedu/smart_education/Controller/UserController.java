package com.smartedu.smart_education.Controller;

import com.smartedu.smart_education.entity.User;
import com.smartedu.smart_education.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getActiveUsers() {
        return ResponseEntity.ok(userService.getActiveUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable User.Role role) {
        return ResponseEntity.ok(userService.getAllUsersByRole(role));
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return ResponseEntity.status(201).body(userService.registerUser(user));
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivateUser(@PathVariable Long id) {
        userService.deactivateUserById(id);
        return ResponseEntity.ok("User deactivated successfully!");
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<String> activateUser(@PathVariable Long id) {
        userService.activateUserById(id);
        return ResponseEntity.ok("User activated successfully!");
    }
}