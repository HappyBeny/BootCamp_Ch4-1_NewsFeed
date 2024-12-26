package com.example.ch4_1_newsfeed.repository;

import com.example.ch4_1_newsfeed.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    List<Photo> findPhotoByFeed_id(Long id);
}
