package com.beaconfire.springsecurityauth.service;

import com.beaconfire.springsecurityauth.client.UserServiceClient;
import com.beaconfire.springsecurityauth.dao.UserDao;
import com.beaconfire.springsecurityauth.dto.CustomUserDetail;
import com.beaconfire.springsecurityauth.dto.UserDTO;
import com.beaconfire.springsecurityauth.security.AuthUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {


    private UserDao userDao;
    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }


//    @PreAuthorize("hasPermission('ADMIN')")
//    @PreAuthorize("hasPermission('NORMAL')")
//    @PreAuthorize("hasPermission('SUPERADMIN')")
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Optional<UserDTO> userOptional = userDao.loadUserByUsername(email);
        List<UserDTO> users = userServiceClient.getAllUsers();


        if (users.isEmpty()){
            throw new UsernameNotFoundException("Username does not exist");
        }

        UserDTO user = users.stream()
                .filter(u -> u.getEmail().equals(email)).findFirst().orElseThrow(() -> new UsernameNotFoundException("Email not found " + email));

       List<GrantedAuthority> authorities = getAuthoritiesRoles(user.getType());

        System.out.println("User: " + user.getEmail() + " role: " + user.getType());
        System.out.println("Authorities: " + authorities);

        System.out.println("Password: " + user.getPassword());

        return CustomUserDetail.builder() // spring security's userDetail
//                .email(user.getEmail())
                .username(user.getEmail())
                .password(user.getPassword())
                .emailVerified(user.isEmailVerified())
                .authorities(authorities)
                .accountNonExpired(user.isActive())
                .accountNonLocked(user.isActive())
                .credentialsNonExpired(user.isActive())
                .enabled(user.isActive())
                .build();
    }
    private List<GrantedAuthority> getAuthoritiesRoles(String role) {
        switch (role) {
            case "Admin":
                return Arrays.asList(new SimpleGrantedAuthority("ADMIN"));
            case "SuperAdmin":
                return Arrays.asList(new SimpleGrantedAuthority("SUPERADMIN"));
            case "Normal":
                return Arrays.asList(new SimpleGrantedAuthority("USER"));
            default:
                throw new IllegalArgumentException("Unknown role: " + role);
        }
    }

//    private List<GrantedAuthority> getAuthoritiesFromUser(UserDTO user){
//        List<GrantedAuthority> userAuthorities = new ArrayList<>();
//
//        for (String permission :  user.getPermissions()){
//            userAuthorities.add(new SimpleGrantedAuthority(permission));
//        }
//
//        return userAuthorities;
//    }
}
