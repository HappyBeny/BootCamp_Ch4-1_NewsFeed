package com.example.ch4_1_newsfeed.dto.feed;

import lombok.Getter;

@Getter
public class FeedRequestDto {

    private final String contents;
    private final String name;

    public FeedRequestDto(String contents, String name) {
        this.contents = contents;
        this.name = name;
    }
}


