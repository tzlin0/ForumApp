package com.example.Final.Project.Forum.service;

import com.example.Final.Project.Forum.entity.Role;
import com.example.Final.Project.Forum.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role addRole(Role role) { return roleRepository.save(role);}

    public List<Role> getAllRoles() {return roleRepository.findAll();}
}
