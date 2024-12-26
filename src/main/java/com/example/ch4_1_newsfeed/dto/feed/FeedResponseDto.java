package com.example.ch4_1_newsfeed.dto.feed;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class FeedResponseDto {

    private final Long id;
    private final String username;
    private final String contents;
    private final LocalDateTime createdAt;
}