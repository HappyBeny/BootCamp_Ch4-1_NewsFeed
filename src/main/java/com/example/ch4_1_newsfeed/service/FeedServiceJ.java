package com.example.ch4_1_newsfeed.service;

import com.example.ch4_1_newsfeed.dto.feed.FeedResponseDto;
import com.example.ch4_1_newsfeed.entity.Feed;
import com.example.ch4_1_newsfeed.entity.User;
import com.example.ch4_1_newsfeed.repository.FeedRepository;
import com.example.ch4_1_newsfeed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedServiceJ {

    private final UserRepository userRepository;
    private final FeedRepository feedRepository;

    public FeedResponseDto save(String contents, String name) {

        User findUser = userRepository.findUserByUsernameOrElseThrow(name);

        Feed feed = new Feed(contents, findUser);

        feedRepository.save(feed);


        return new FeedResponseDto(
                feed.getId(),
                findUser.getName(),
                feed.getContents(),
                feed.getCreatedAt());

    }
    public void delete(Long id) {

        Feed findFeed = feedRepository.findByIdOrElseThrow(id);

        feedRepository.delete(findFeed);
    }


}




