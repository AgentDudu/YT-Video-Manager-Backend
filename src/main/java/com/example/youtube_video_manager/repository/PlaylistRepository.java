package com.example.youtube_video_manager.repository;

import com.example.youtube_video_manager.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

  List<Playlist> findByUserId(Long userId);
}
