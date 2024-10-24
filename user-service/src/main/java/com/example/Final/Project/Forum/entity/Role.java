package com.example.Final.Project.Forum.entity;

import javax.persistence.*;

@Entity
@Table(name = "Role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(nullable = false, length = 50)
    private String roleName;

    public Long getRoleId() { return roleId;}

    public void setRoleId(Long roleId) { this.roleId = roleId;}

    public String getRoleName() { return roleName;}

    public void setRoleName(String roleName) { this.roleName = roleName;}
}
