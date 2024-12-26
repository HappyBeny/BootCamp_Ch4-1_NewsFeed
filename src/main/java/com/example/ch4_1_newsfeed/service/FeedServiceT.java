package com.example.ch4_1_newsfeed.service;

import com.example.ch4_1_newsfeed.dto.feed.FeedResponseDto;
import com.example.ch4_1_newsfeed.dto.feed.request.ModifyFeedRequestDto;
import com.example.ch4_1_newsfeed.dto.feed.response.FindAllFeedResponseDto;
import com.example.ch4_1_newsfeed.dto.feed.response.FindByUserAndFeedIdResponseDto;
import com.example.ch4_1_newsfeed.dto.feed.response.FindByUserIdResponseDto;
import com.example.ch4_1_newsfeed.entity.Feed;
import com.example.ch4_1_newsfeed.entity.Photo;
import com.example.ch4_1_newsfeed.repository.FeedRepository;
import com.example.ch4_1_newsfeed.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedServiceT {

    private final FeedRepository feedRepository;
    private final PhotoRepository photoRepository;

    /**
     * 모든 피드 조회<br>
     * 현재 모든 Feed 데이터를 가져오고 있음<br>
     * 페이지네이션 적용 필요함
     */
    public List<FindAllFeedResponseDto> findAllFeeds(int page, int size) {
        // Feed 데이터를 모두 가져옴 (실제 서비스에서는 페이징 적용 필요)
        List<Feed> feeds = feedRepository.findAll();

        return feeds.stream()
                .map(feed -> new FindAllFeedResponseDto(
                        feed.getId(),
                        feed.getUser().getId(),
                        feed.getContents(),
                        feed.getCreatedAt(),
                        photoRepository.findPhotoByFeed_id(feed.getId())
                )).toList();
    }

    /**
     * 특정 id 뉴스피드 조회
     */
    public List<FindByUserIdResponseDto> findByUserId(Long user_id) {
        return feedRepository.findAllByUserId(user_id).stream()
                .map(feed -> new FindByUserIdResponseDto(
                        feed.getId(),
                        feed.getUser().getName(),
                        feed.getContents(),
                        feed.getCreatedAt(),
                        photoRepository.findPhotoByFeed_id(user_id)
                )).toList();
    }

    /**
     * 특정 뉴스피드 조회
     */
    public FindByUserAndFeedIdResponseDto findByUserAndFeed(Long user_id, Long feed_id) {
        Feed byIdAndId = feedRepository.findByIdAndId(user_id, feed_id);
        List<Photo> photos = photoRepository.findPhotoByFeed_id(feed_id);

        return new FindByUserAndFeedIdResponseDto(
                byIdAndId.getId(),
                byIdAndId.getContents(),
                byIdAndId.getUser(),
                photos,
                byIdAndId.getCreatedAt()
        );
    }

    /**
     * 피드 수정
     */
    public FeedResponseDto updateFeed(Long feed_id, ModifyFeedRequestDto dto) {
        Feed feed = feedRepository.findById(feed_id)
                .orElseThrow(() -> new IllegalArgumentException("NOT FOUND"));

        feed.updateFeed(dto.getContents());

        return new FeedResponseDto(
                feed.getId(),
                feed.getUser().getName(),
                feed.getContents(),
                feed.getCreatedAt()
        );
    }
}
