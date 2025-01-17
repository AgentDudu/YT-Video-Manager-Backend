package com.example.youtube_video_manager.dto;

import com.example.youtube_video_manager.model.Video;
import lombok.Data;

import java.util.Set;

@Data
public class PlaylistResponseDto {

  private Long id;
  private String name;
  private Set<Video> videos;
}
