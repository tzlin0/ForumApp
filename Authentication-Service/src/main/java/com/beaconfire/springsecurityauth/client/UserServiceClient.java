package com.beaconfire.springsecurityauth.client;

import com.beaconfire.springsecurityauth.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("user-service")
public interface UserServiceClient {
    @GetMapping("/api/users")
    List<UserDTO> getAllUsers();
}
