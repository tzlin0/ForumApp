package com.example.Final.Project.Forum.controller;



import com.example.Final.Project.Forum.dto.EmailRequestDTO;
import com.example.Final.Project.Forum.dto.ProfileImageResponse;
import com.example.Final.Project.Forum.entity.Role;
import com.example.Final.Project.Forum.entity.User;
import com.example.Final.Project.Forum.exception.DuplicateEmailException;
import com.example.Final.Project.Forum.exception.UserNotFoundException;
import com.example.Final.Project.Forum.repo.UserRepository;
import com.example.Final.Project.Forum.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
//import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${JWT_SECRETKEY}")
    private String JWT_SECRET;

    @Value("${email.exchange}")
    private String exchange;

    @Value("${email.queue}")
    private String queue;

    @Value("${email.routingkey}")
    private String routingKey;

    @Value("${email.secretKey}")
    private String secretKey;


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public List<User> getAllUser(Authentication authentication){

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Print authorities to the console (you can log or handle this as needed)
        authorities.forEach(authority -> System.out.println("Authority: " + authority.getAuthority()));

        System.out.println("get all user");
        return userService.getAllUsers();
    }

    @GetMapping("/connection")
    public String testConnection() {
        try {
            jdbcTemplate.execute("SELECT 1");
            return "Database connection is successful";
        } catch (Exception e) {
            return "Database connection failed: " + e.getMessage();
        }
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        System.out.println("registerUser");
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateEmailException("Email already exists");
        }

        try {
            // to user service
            userService.registerUser(user);
            return "User registered successfully";
        } catch (Exception e) {
            return "User registration failed: " + e.getMessage();
        }
    }

    @GetMapping("/api/users/{userId}")
    public User getUser(@PathVariable Long userId) {
        User user = null;
        try {
            user = userService.getUserById(userId);
            // to user service

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @PostMapping("/verify")
//    @PreAuthorize("")
    public String verifyUser(@RequestBody Integer code) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String role = authentication.getAuthorities().iterator().next().getAuthority();
//        System.out.println(role+" get --PLACE NEW ORDER----");

        String email =authentication.getName();
        System.out.println("email: "+ email);
        userService.verifyUser(email, String.valueOf(code));
        return "User verified successfully";

    }


    @PutMapping("/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody User user) {

        try {
            // to user service
            user.setUserId(userId);
            return userService.updateUser(user);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        if (!userRepository.findById(userId).isPresent()) {
            throw new UserNotFoundException("User not found");
        }
        try {
            // delete user
            userService.deleteUser(userId);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "User deleted successfully";
    }

    @GetMapping("profileImage/{userId}")
    public String getUrlById(@PathVariable Long userId) { return userService.profileImageUrl(userId);}

    @PutMapping("profileImage/{userId}")
    public ResponseEntity<ProfileImageResponse> updateProfileImage(@PathVariable String userId, @RequestParam String imageUrl) {
        ProfileImageResponse response = userService.updateUserProfileImage(Long.valueOf(userId), imageUrl);
        return ResponseEntity.ok(response);
    }

@GetMapping("/email/send")
public void sendEmail(@RequestParam String email) {
    // Generate JWT token for email verification
    String to = email;
    String subject = "Verification Email for Forum App";
    String body = "";

    System.out.println("Send to: " + email + " for verification purpose");

    String token = Jwts.builder()
            .setSubject(to)
            .setExpiration(new Date(System.currentTimeMillis() + 3 * 60 * 60 * 1000)) // 3 hours expiration
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact();

    // create the email request,
    EmailRequestDTO emailRequest = new EmailRequestDTO(to, subject, body, token);
//    emailRequest.setTo(to);
//    emailRequest.setSubject(subject);
//    emailRequest.setBody(body);
//    emailRequest.setToken(token);

    // sed the email request to RabbitMQ
    rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    rabbitTemplate.convertAndSend(exchange, routingKey, emailRequest);
}
    @GetMapping("/email/verify")
    public ResponseEntity<?> verifyUser(@RequestParam String token) {
        System.out.println(secretKey);
        try {
            // Parse and validate the token
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.getSubject();

            Date expiration = claims.getExpiration();
            System.out.println("Verification success for email: " + email + ". Token will expire at " + expiration);
            // Update user email verification status
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.setEmailVerified(true);
                userRepository.save(user);
            } else {
                throw new RuntimeException("User not found");
            }

        }catch (Exception e){

            return ResponseEntity.badRequest().body("Invalid token.");
        }
        return ResponseEntity.ok("Email verified successfully!");
    }





}
