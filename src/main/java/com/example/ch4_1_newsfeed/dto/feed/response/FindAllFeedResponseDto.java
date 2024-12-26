package com.example.ch4_1_newsfeed.dto.feed.response;

import com.example.ch4_1_newsfeed.entity.Photo;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;

@Getter

public class FindAllFeedResponseDto {
    private Long feed_id;
    private Long following_id;
    private String description;
    private LocalDateTime createdAt;
    private List<Photo> photos;

    public FindAllFeedResponseDto(Long feed_id, Long following_id, String description, LocalDateTime createdAt, List<Photo> photos) {
        this.feed_id = feed_id;
        this.following_id = following_id;
        this.description = description;
        this.createdAt = createdAt;
        this.photos = photos;
    }
}
