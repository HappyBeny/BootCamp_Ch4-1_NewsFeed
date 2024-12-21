package com.example.ch4_1_newsfeed.controller;

import com.example.ch4_1_newsfeed.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class UserDto {

    private Long id;
    private String email;
    private String name;

    private UserDto(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public static UserDto from(User user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getName()
        );
    }

}
