package com.example.youtube_video_manager.controller;

import com.example.youtube_video_manager.dto.VideoDto;
import com.example.youtube_video_manager.dto.VideoResponseDto;
import com.example.youtube_video_manager.service.VideoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/videos")
public class VideoController {
  private final VideoService videoService;

  public VideoController(VideoService videoService) {
    this.videoService = videoService;
  }

  @PostMapping
  public ResponseEntity<VideoResponseDto> addVideo(@RequestBody VideoDto videoDto) {
    VideoResponseDto videoResponseDto = videoService.addVideo(videoDto);
    return new ResponseEntity<>(videoResponseDto, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<VideoResponseDto>> getAllVideos() {
    List<VideoResponseDto> videoResponseDtoList = videoService.getAllVideos();
    return new ResponseEntity<>(videoResponseDtoList, HttpStatus.OK);
  }

  @PostMapping("/sort")
  public ResponseEntity<List<VideoResponseDto>> sortVideos(@RequestParam String sortBy) {
    List<VideoResponseDto> videoResponseDtoList = videoService.sortVideos(sortBy);
    return new ResponseEntity<>(videoResponseDtoList, HttpStatus.OK);
  }

  @GetMapping("/search")
  public ResponseEntity<List<VideoResponseDto>> searchVideos(@RequestParam String q) {
    List<VideoResponseDto> videoResponseDtoList = videoService.searchVideos(q);
    return new ResponseEntity<>(videoResponseDtoList, HttpStatus.OK);
  }
}
