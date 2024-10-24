package com.example.Final.Project.Forum.service;

import com.example.Final.Project.Forum.dto.ProfileImageResponse;
import com.example.Final.Project.Forum.entity.Role;
import com.example.Final.Project.Forum.entity.User;
import com.example.Final.Project.Forum.entity.UserRole;
import com.example.Final.Project.Forum.exception.DuplicateEmailException;
import com.example.Final.Project.Forum.exception.InvalidEmailVerificationCode;
import com.example.Final.Project.Forum.exception.UserAlreadyVerifiedException;
import com.example.Final.Project.Forum.exception.UserNotFoundException;
import com.example.Final.Project.Forum.repo.RoleRepository;
import com.example.Final.Project.Forum.repo.UserRepository;
import com.example.Final.Project.Forum.repo.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RabbitMQProducer rabbitMQProducer;

    @PreAuthorize("hasAuthority('NORMAL')")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public User putUser(User user) {
        return userRepository.save(user);
    }



    // registerUser() -> save user to DB
    // check if email exists in DB
    // send the email
    // logic: Unverified email can be registered by another person/ user
    public User registerUser(User user) throws RuntimeException {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent() && existingUser.get().getEmailVerified()) {
            throw new DuplicateEmailException("Verified user already exists");
        }
        // password encryption
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRegistrationDate();
        user.setActive(false); // set to false after
        user.setEmailVerified(false); // set to false after
        // send the email
        String email = user.getEmail();
        String verificationCode = generateVerificationCode();
        // save code to User db
        user.setCode(verificationCode);



        if(!Arrays.asList("Normal", "Admin", "SuperAdmin").contains(user.getType())) {
            user.setType("Normal");
        }
        User savedUser = userRepository.save(user);

        System.out.println("User: " + savedUser + " saved");
        System.out.println("verificationCode: " + verificationCode);
//        rabbitMQProducer.sendEmailVerification(savedUser.getEmail(), verificationCode);
        System.out.println("Email: " + savedUser.getEmail());

        // create user role
        Role useRole = roleRepository.findByRoleName(user.getType()).
                orElseThrow(() -> new RuntimeException(user.getType() + "role not found "));

        // create and save the UserRole entry
        UserRole userRoleEntry = new UserRole();
        userRoleEntry.setRole(useRole);
        userRoleEntry.setUser(savedUser);
        userRoleRepository.save(userRoleEntry);



        return savedUser;
    }
    // verifyUser() -> check code
    // change the status

    // boolean method(User user, String code)

    public void verifyUser(String email, String code) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (!existingUser.isPresent()) {
            throw new UserNotFoundException("User not found");
        }
        if (!existingUser.get().getCode().equals(code)) {
            throw new InvalidEmailVerificationCode("Invalid email verification code");
        }
        existingUser.get().setEmailVerified(true);  // set to true after verified
        existingUser.get().setActive(true);  // set to true after verified

        userRepository.save(existingUser.get());
    }

    public String generateVerificationCode() {
        int length = 6;
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10); // Generate a random digit between 0-9
            sb.append(digit);
        }

        return sb.toString(); // Returns a string like "123456"
    }

    public User updateEmail(Long userId, String newEmail) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (!existingUser.isPresent()) {
            throw new UserNotFoundException("User not found");
        }
        User user = existingUser.get();
        user.setEmail(newEmail);
        user.setEmailVerified(false);
        return userRepository.save(user);
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // save the new user details
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setActive(user.getActive());
        existingUser.setType(user.getType());
        existingUser.setNewtRegistrationDate(user.getRegistrationDate());
        existingUser.setEmailVerified(false);
        return userRepository.save(existingUser);
    }

    public ProfileImageResponse updateUserProfileImage(Long userId, String imageUrl) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setProfileImageUrl(imageUrl);
        User updatedUser = userRepository.save(user);

        return ProfileImageResponse.builder().userId(String.valueOf(updatedUser.getUserId()))
                .imageUrl(updatedUser.getProfileImageUrl()).build();
    }

    public String profileImageUrl(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        return user.getProfileImageUrl();
    }

    //

}
