package com.example.Final.Project.Forum.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
public class SimpleMessage implements Serializable {
    private String title;
    private String description;
}
