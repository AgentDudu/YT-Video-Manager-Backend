package com.example.youtube_video_manager.dto;

import lombok.Data;

@Data
public class VideoDto {

  private String url;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
