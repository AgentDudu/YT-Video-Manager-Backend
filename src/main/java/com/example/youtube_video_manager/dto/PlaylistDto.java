package com.example.youtube_video_manager.dto;

import lombok.Data;

import java.util.List;

@Data
public class PlaylistDto {

  private String name;

  private List<Long> videoIds;
}
