package com.example.ch4_1_newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "feed")
public class Feed extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "longtext", nullable = false)
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @CreatedDate
    private LocalDateTime createdAt;

    public Feed() {
    }

    public Feed(String contents, User user) {
        this.contents = contents;
        this.user = user;
    }

    public void updateFeed(String contents) {
        this.contents = this.contents;
    }
}
