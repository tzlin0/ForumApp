package com.example.Final.Project.Forum.dto;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;

import java.io.Serializable;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonTypeName("com.example.Final.Project.Forum.dto.EmailRequestDTO")
public class EmailRequestDTO implements Serializable {
    private String to;
    private String subject;
    private String body;
    private String token;
//    public EmailRequestDTO(String to, String subject, String body, String token) {
//        this.to = to;
//        this.
//    }
}

