package com.example.Final.Project.Forum.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProfileImageResponse {
    String userId;
    String imageUrl;
}
