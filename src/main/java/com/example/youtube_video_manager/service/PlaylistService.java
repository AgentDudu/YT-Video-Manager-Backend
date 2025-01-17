package com.example.youtube_video_manager.service;

import com.example.youtube_video_manager.dto.PlaylistDto;
import com.example.youtube_video_manager.dto.PlaylistResponseDto;
import com.example.youtube_video_manager.model.Playlist;
import com.example.youtube_video_manager.model.User;
import com.example.youtube_video_manager.model.Video;
import com.example.youtube_video_manager.repository.PlaylistRepository;
import com.example.youtube_video_manager.repository.UserRepository;
import com.example.youtube_video_manager.repository.VideoRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlaylistService {
  private final PlaylistRepository playlistRepository;
  private final VideoRepository videoRepository;
  private final UserRepository userRepository;

  public PlaylistService(PlaylistRepository playlistRepository, VideoRepository videoRepository,
      UserRepository userRepository) {
    this.playlistRepository = playlistRepository;
    this.videoRepository = videoRepository;
    this.userRepository = userRepository;
  }

  public PlaylistResponseDto createPlaylist(PlaylistDto playlistDto) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    Optional<User> user = userRepository.findByUsername(username);

    if (user.isPresent()) {

      Playlist playlist = new Playlist();
      playlist.setName(playlistDto.getName());
      Set<Video> videos = new HashSet<>();
      if (playlistDto.getVideoIds() != null) {
        for (Long videoId : playlistDto.getVideoIds()) {
          Optional<Video> video = videoRepository.findById(videoId);
          video.ifPresent(videos::add);
        }
        playlist.setVideos(videos);
      }

      playlist.setUser(user.get());

      Playlist savedPlaylist = playlistRepository.save(playlist);

      PlaylistResponseDto playlistResponseDto = new PlaylistResponseDto();
      playlistResponseDto.setId(savedPlaylist.getId());
      playlistResponseDto.setName(savedPlaylist.getName());
      playlistResponseDto.setVideos(savedPlaylist.getVideos());

      return playlistResponseDto;

    }

    return null;
  }

  public List<PlaylistResponseDto> getAllPlaylists() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    Optional<User> user = userRepository.findByUsername(username);

    if (user.isPresent()) {
      List<Playlist> playlists = playlistRepository.findByUserId(user.get().getId());
      return playlists.stream()
          .map(this::convertToPlaylistResponseDto)
          .collect(Collectors.toList());
    }
    return null;
  }

  private PlaylistResponseDto convertToPlaylistResponseDto(Playlist playlist) {
    PlaylistResponseDto playlistResponseDto = new PlaylistResponseDto();
    playlistResponseDto.setId(playlist.getId());
    playlistResponseDto.setName(playlist.getName());
    playlistResponseDto.setVideos(playlist.getVideos());
    return playlistResponseDto;
  }
}
