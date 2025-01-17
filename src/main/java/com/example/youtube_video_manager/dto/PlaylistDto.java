package com.example.youtube_video_manager.dto;

import lombok.Data;

import java.util.List;

@Data
public class PlaylistDto {

  private String name;

  private List<Long> videoIds;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Long> getVideoIds() {
    return videoIds;
  }

  public void setVideoIds(List<Long> videoIds) {
    this.videoIds = videoIds;
  }
}
