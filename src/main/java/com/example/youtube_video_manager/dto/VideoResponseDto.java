package com.example.youtube_video_manager.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VideoResponseDto {

  private Long id;
  private String url;
  private String title;
  private String channelName;
  private String thumbnailUrl;
  private LocalDateTime dateAdded;
}
