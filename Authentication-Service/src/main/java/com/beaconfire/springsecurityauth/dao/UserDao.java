package com.beaconfire.springsecurityauth.dao;

import com.beaconfire.springsecurityauth.dto.UserDTO;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDao {

    // should be stored in the database
    // user table,permission table
    private final List<UserDTO> users = Arrays.asList(
//            new UserDTO("user", "user", Arrays.asList("read")),
//            new UserDTO("rep", "rep", Arrays.asList("write")),
//            new UserDTO("admin", "admin", Arrays.asList("delete", "read", "update", "write"))
    );

    public Optional<UserDTO> loadUserByUsername(String username){
        return users.stream().filter(user -> username.equals(user.getEmail())).findAny();
    }

}
