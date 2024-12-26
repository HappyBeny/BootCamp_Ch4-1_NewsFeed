package com.example.ch4_1_newsfeed.dto.feed.response;

import com.example.ch4_1_newsfeed.entity.Photo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class ModifyFeedResponseDto {

    private final Long id;
    private final String contents;
    private final List<Photo> photos;
    private final LocalDateTime createdAt;
}