package com.example.youtube_video_manager.repository;

import com.example.youtube_video_manager.model.Video;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

  List<Video> findByUserId(Long userId);

  List<Video> findByUserId(Long userId, Sort sort);

  List<Video> findByTitleContainingIgnoreCaseAndUserId(String title, Long userId);

  List<Video> findByChannelNameContainingIgnoreCaseAndUserId(String channelName, Long userId);
}
