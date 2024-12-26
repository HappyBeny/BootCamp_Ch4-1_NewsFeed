package com.example.ch4_1_newsfeed.repository;


import com.example.ch4_1_newsfeed.entity.Relationship;
import com.example.ch4_1_newsfeed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RelationshipRepository extends JpaRepository<Relationship, Long> {

    Optional<Relationship> findRelationshipByFollower_IdAndFollowee_id(Long followerId, Long followeeId);

}
