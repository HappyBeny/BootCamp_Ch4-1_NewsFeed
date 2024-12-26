package com.example.ch4_1_newsfeed.dto.feed.response;

import com.example.ch4_1_newsfeed.entity.Photo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class FindByUserIdResponseDto {
    private final Long userId;
    private final String userName;
    private final String contents;
    private final LocalDateTime createdAt;
    private final List<Photo> photos;
}