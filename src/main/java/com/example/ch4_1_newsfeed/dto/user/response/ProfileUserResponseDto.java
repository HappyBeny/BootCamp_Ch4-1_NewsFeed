package com.example.ch4_1_newsfeed.dto.user.response;

import com.example.ch4_1_newsfeed.entity.Feed;
import com.example.ch4_1_newsfeed.entity.User;
import lombok.Getter;

import java.util.List;

@Getter
public class ProfileUserResponseDto {

    private Long id;
    private String name;
    private String pictureUrl;
    private List<Feed> feeds;

    private ProfileUserResponseDto(Long id, String name, String pictureUrl, List<Feed> feeds) {
        this.id = id;
        this.name = name;
        this.pictureUrl = pictureUrl;
        this.feeds = feeds;
    }


    /**
     * 정적 팩토리 메소드
     * @param user
     * @param userFeeds
     * @return
     */
    public static ProfileUserResponseDto from(User user, List<Feed> userFeeds) {
        return new ProfileUserResponseDto(
                user.getId(),
                user.getName(),
                user.getProfilePicture(),
                userFeeds
        );
    }
}
