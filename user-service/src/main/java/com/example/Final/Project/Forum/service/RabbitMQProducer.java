package com.example.Final.Project.Forum.service;

import com.example.Final.Project.Forum.config.RabbitMQConfig;
import com.example.Final.Project.Forum.dto.EmailRequestDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendEmailVerification(String email, String verificationCode) {
        EmailRequestDTO request = new EmailRequestDTO(email, "", "", verificationCode);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EMAIL_QUEUE, request);
    }
}
//@Getter
//@Setter
//class EmailVerificationRequest {
//    private String email;
//    private String verificationCode;
//
//    // Constructor, getters, setters, etc.
//
//    public EmailVerificationRequest(String email, String verificationCode) {
//        this.email = email;
//        this.verificationCode = verificationCode;
//    }
//
//    // Getters and setters
//}
