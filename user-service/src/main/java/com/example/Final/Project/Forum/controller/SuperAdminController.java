package com.example.Final.Project.Forum.controller;

import com.example.Final.Project.Forum.entity.User;
import com.example.Final.Project.Forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class SuperAdminController {

    @Autowired
    private UserService userService;

    @PutMapping("/{userId}/promote")
    public void promote(@PathVariable int userId){
        User user = userService.getUserById(userId);
        if (user != null){
            user.setType("Admin");
            userService.updateUser(user);
        }
    }
}
