package com.example.ch4_1_newsfeed.dto.feed.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FeedRequestDto {

    private String contents;
    private String name;
}

