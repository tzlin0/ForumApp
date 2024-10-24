package com.example.Final.Project.Forum.entity;

import javax.persistence.*;

@Entity
@Table(name = "UserRole")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userRoleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleId", referencedColumnName = "roleId")
    private Role role;

    public Long getUserRoleId() {return userRoleId;}

    public void setUserRoleId(Long userRoleId) {this.userRoleId = userRoleId;}

    public User getUser() {return user;}

    public void setUser(User user) {this.user = user;}

    public void setRole(Role role) {this.role = role;}
}
