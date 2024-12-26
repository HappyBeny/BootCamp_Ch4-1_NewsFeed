package com.example.ch4_1_newsfeed.service;

import com.example.ch4_1_newsfeed.dto.user.response.*;
import com.example.ch4_1_newsfeed.dto.user.request.*;
import com.example.ch4_1_newsfeed.encode.PasswordEncoder;
import com.example.ch4_1_newsfeed.entity.Feed;
import com.example.ch4_1_newsfeed.entity.Relationship;
import com.example.ch4_1_newsfeed.entity.User;
import com.example.ch4_1_newsfeed.repository.FeedRepository;
import com.example.ch4_1_newsfeed.repository.RelationshipRepository;
import com.example.ch4_1_newsfeed.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FeedRepository feedRepository;
    private final PasswordEncoder passwordEncoder;
    private final RelationshipRepository relationshipRepository;

    /**
     * 유저의 이메일로 유저 아이디를 찾음.
     * @param
     * @return
     */
    public UserResponseDto getUserId(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("이메일이 존재하지 않습니다."));
        return UserResponseDto.from(user);
    }

    /**
     * 로그인 기능 추가
     * @param : 이메일, encode 된 비밀번호 포함
     */
    public UserResponseDto loginUser(LoginUserRequestDto request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalStateException("이메일이 존재하지 않습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        return UserResponseDto.from(user);
    }

    public SignUpUserResponseDto createUser(SignUpUserRequestDto request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = User.createUser(request,encodedPassword);
        /**
         * 비밀번호 암호화 기능 추가
         */

        userRepository.save(user);
        return SignUpUserResponseDto.from(user);
    }

    public ProfileUserResponseDto getMyProfile(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("내 정보가 존재하지 않습니다."));
        List<Feed> feedList = feedRepository.findAllByUserId(user.getId());

        return ProfileUserResponseDto.from(user, feedList);
    }

    // 트랜잭셔널 추가
    @Transactional
    public UpdateUserResponseDto updateMyProfile(Long userId, UpdateUserRequestDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("내 정보가 존재하지 않습니다."));
        user.updateUser(request);
        return UpdateUserResponseDto.from(user);
    }

    @Transactional
    public void updateMyPassword(Long userId, UpdatePasswordUserRequestDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("내 정보가 존재하지 않습니다."));
        user.updateUserPassword(request);
    }

    public ProfileUserResponseDto getUserProfile(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("찾으시는 사용자가 존재하지 않습니다."));
        List<Feed> feedList = feedRepository.findAllByUserId(user.getId());
        return ProfileUserResponseDto.from(user, feedList);
    }

    public void deleteUser(Long userId, DeleteUserRequestDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("내 정보가 존재하지 않습니다."));

        if (user.getPassword() != request.getPassword()) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
        userRepository.delete(user);
    }


    /**
     * 팔로우/언팔
     */
    public RelationshipResponseDto follow(Long followeeId, HttpSession session) {

        Long userId = (Long) session.getAttribute("user");
        User following = userRepository.findById(userId).orElseThrow();
        User followed = userRepository.findById(followeeId).orElseThrow();

        Optional<Relationship> foundRelationship =
                relationshipRepository.findRelationshipByFollower_IdAndFollowee_id
                        (following.getId(), followed.getId());

        if (foundRelationship.isEmpty()) {

            Relationship relationship = new Relationship(following, followed);
            relationshipRepository.save(relationship);
            return new RelationshipResponseDto(following,followed,"you followed " + followed.getName());

        }

        relationshipRepository.delete(foundRelationship.get());
        return new RelationshipResponseDto(following, followed, "you unfollowed " + followed.getName());
    }
}
