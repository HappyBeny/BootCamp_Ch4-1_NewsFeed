package com.example.ch4_1_newsfeed.dto.feed.request;

import lombok.Getter;

@Getter
public class ModifyFeedRequestDto {

    private Long feed_id;
    private String contents;
}
