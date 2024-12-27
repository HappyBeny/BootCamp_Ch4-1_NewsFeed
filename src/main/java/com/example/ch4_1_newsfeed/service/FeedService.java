package com.example.ch4_1_newsfeed.service;

import com.example.ch4_1_newsfeed.common.SessionConst;
import com.example.ch4_1_newsfeed.model.dto.feed.request.ModifyFeedRequestDto;
import com.example.ch4_1_newsfeed.model.dto.feed.response.*;
import com.example.ch4_1_newsfeed.model.dto.user.response.ProfileUserResponseDto;
import com.example.ch4_1_newsfeed.model.entity.Feed;
import com.example.ch4_1_newsfeed.model.entity.Photo;
import com.example.ch4_1_newsfeed.model.entity.User;
import com.example.ch4_1_newsfeed.repository.FeedRepository;
import com.example.ch4_1_newsfeed.repository.PhotoRepository;
import com.example.ch4_1_newsfeed.repository.RelationshipRepository;
import com.example.ch4_1_newsfeed.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.ch4_1_newsfeed.common.SessionConst.LOGIN_USER;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedService {

    private final UserRepository userRepository;
    private final FeedRepository feedRepository;
    private final PhotoRepository photoRepository;
    private final RelationshipRepository relationshipRepository;
    private final HttpSession session;

    /**
     * 피드 생성하기
     */
    public FeedResponseDto save(String contents, String name) {

        User findUser = userRepository.findUserByUsernameOrElseThrow(name);

        Feed feed = new Feed(contents, findUser);

        feedRepository.save(feed);


        return FeedResponseDto.from(feed);
    }

    /**
     * 모든 피드 조회<br>
     * 세션에서 로그인한 사용자의 ID 반환
     * Relationship 에서 사용자가 팔로우한 User 리스트 반환
     * 리스트에 포함된 유저들의 모든 게시글 반환
     * 게시글마다 사진 리스트 생성자에 주입
     * 페이지네이션 적용 필요함
     */
    public Page<FindAllFeedResponseDto> findAllFeeds(int page, int size) {
        // Feed 데이터를 모두 가져옴 (실제 서비스에서는 페이징 적용 필요)
        Long userId = (Long) session.getAttribute(LOGIN_USER);
        log.info("111111111111111111111111");
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<FindAllFeedResponseDto> dtos = feedRepository.findFeedsByUserRelationships(userId, pageRequest);
        for(FindAllFeedResponseDto dto : dtos) {
            dto.setPhotos(photoRepository.findPhotoUrlByFeedIds(dto.getFeedId()));
        }
        return dtos;
    }

    /**
     * 특정 id 뉴스피드 조회
     */
    public FindAllFeedsByUserIdDto findByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("없는 사용자입니다."));
        List<Feed> feeds = feedRepository.findByUser_Id(user.getId());
        log.info("feeds : {} ", feeds);
        return FindAllFeedsByUserIdDto.from(user, feeds);
    }

    /**
     * 특정 뉴스피드 조회
     */
    public FindByUserAndFeedIdResponseDto findByUserAndFeed(Long userId, Long feedId) {
        Feed byIdAndId = feedRepository.findByIdAndId(userId, feedId);
        List<Photo> photos = photoRepository.findPhotoByFeedId(feedId);

        return FindByUserAndFeedIdResponseDto.from(byIdAndId, photos);
    }

    /**
     * 내 id 뉴스피드 조회
     */
    public ProfileUserResponseDto getMyProfile(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("내 정보가 존재하지 않습니다."));
        List<Feed> feedList = feedRepository .findByUser_Id(user.getId());

        return ProfileUserResponseDto.from(user, feedList);
    }

    /**
     * 피드 수정
     */
    @Transactional
    public FeedResponseDto updateFeed(Long feedId, ModifyFeedRequestDto dto) {
        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new IllegalArgumentException("NOT FOUND"));

        feed.updateFeed(dto.getContents());

        return FeedResponseDto.from(feed);
    }

    /**
     * 피드 삭제
     */
    public void delete(Long id) {

        Feed findFeed = feedRepository.findByIdOrElseThrow(id);

        feedRepository.delete(findFeed);
    }
}
