package com.example.ch4_1_newsfeed.dto.user.response;

import com.example.ch4_1_newsfeed.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SignUpUserResponseDto {

    private Long id;
    private String email;
    private String name;
    private LocalDateTime createdAt;

    private SignUpUserResponseDto(Long id, String email, String name, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.createdAt = createdAt;
    }

    public static SignUpUserResponseDto from (User user) {
        return new SignUpUserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getCreatedDate()
        );
    }
}
