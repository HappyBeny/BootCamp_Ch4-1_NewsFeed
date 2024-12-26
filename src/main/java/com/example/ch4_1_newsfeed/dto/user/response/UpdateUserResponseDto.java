package com.example.ch4_1_newsfeed.dto.user.response;

import com.example.ch4_1_newsfeed.entity.User;
import lombok.Getter;

@Getter
public class UpdateUserResponseDto {

    private Long id;
    private String email;
    private String name;
    private String pictureUrl;

    private UpdateUserResponseDto(Long id, String email, String name, String pictureUrl) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.pictureUrl = pictureUrl;
    }

    public static UpdateUserResponseDto from(User user) {
        return new UpdateUserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getProfilePicture()
        );
    }
}
