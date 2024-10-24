package com.example.Final.Project.Forum.service;

import com.example.Final.Project.Forum.entity.UserRole;
import com.example.Final.Project.Forum.repo.RoleRepository;
import com.example.Final.Project.Forum.repo.UserRoleRepository;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    public UserRole addRole(UserRole userRole) { return userRoleRepository.save(userRole);}

    public void removeRoleFromUser(Long userRoleId) {userRoleRepository.deleteById(userRoleId);}

    public List<UserRole> getUserRoles(Long userId) {return userRoleRepository.findByUserUserId(userId);}


}
