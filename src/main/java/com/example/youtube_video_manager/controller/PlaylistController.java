package com.example.youtube_video_manager.controller;

import com.example.youtube_video_manager.dto.PlaylistDto;
import com.example.youtube_video_manager.dto.PlaylistResponseDto;
import com.example.youtube_video_manager.service.PlaylistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {
  private final PlaylistService playlistService;

  public PlaylistController(PlaylistService playlistService) {
    this.playlistService = playlistService;
  }

  @PostMapping
  public ResponseEntity<PlaylistResponseDto> createPlaylist(@RequestBody PlaylistDto playlistDto) {
    PlaylistResponseDto playlistResponseDto = playlistService.createPlaylist(playlistDto);
    return new ResponseEntity<>(playlistResponseDto, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<PlaylistResponseDto>> getAllPlaylists() {
    List<PlaylistResponseDto> playlistResponseDtoList = playlistService.getAllPlaylists();
    return new ResponseEntity<>(playlistResponseDtoList, HttpStatus.OK);
  }
}
