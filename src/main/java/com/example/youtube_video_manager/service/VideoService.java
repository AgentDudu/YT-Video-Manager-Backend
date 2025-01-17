package com.example.youtube_video_manager.service;

import com.example.youtube_video_manager.dto.VideoDto;
import com.example.youtube_video_manager.dto.VideoResponseDto;
import com.example.youtube_video_manager.model.User;
import com.example.youtube_video_manager.model.Video;
import com.example.youtube_video_manager.repository.UserRepository;
import com.example.youtube_video_manager.repository.VideoRepository;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VideoService {

    private final VideoRepository videoRepository;
    private final UserRepository userRepository;

    public VideoService(VideoRepository videoRepository, UserRepository userRepository) {
        this.videoRepository = videoRepository;
        this.userRepository = userRepository;
    }


    public VideoResponseDto addVideo(VideoDto videoDto) {

        String url = videoDto.getUrl();

        String videoTitle = null;
        String channelName = null;
        String thumbnailUrl = null;

        try {
            Document doc = Jsoup.connect(url).get();

            Element titleElement = doc.selectFirst("meta[name=title]");
            if (titleElement != null) {
                videoTitle = titleElement.attr("content");
            }

            Element channelElement = doc.selectFirst("link[itemprop=channelId]");
            if (channelElement != null) {
                Element channelLink = doc.selectFirst("link[itemprop=channelId]").nextElementSibling();
                channelName = channelLink.attr("content");
            }


             Element thumbnailElement = doc.selectFirst("link[rel=image_src]");
             if (thumbnailElement != null){
                 thumbnailUrl = thumbnailElement.attr("href");
             }


        } catch (IOException e) {
            log.error("Error extracting metadata: " + e.getMessage());
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userRepository.findByUsername(username);

        if(user.isPresent()){
            Video video = new Video();
            video.setUrl(url);
            video.setTitle(videoTitle);
            video.setChannelName(channelName);
            video.setThumbnailUrl(thumbnailUrl);
            video.setDateAdded(LocalDateTime.now());
            video.setUser(user.get());
            Video savedVideo = videoRepository.save(video);

            VideoResponseDto videoResponseDto = new VideoResponseDto();
            videoResponseDto.setId(savedVideo.getId());
            videoResponseDto.setUrl(savedVideo.getUrl());
            videoResponseDto.setTitle(savedVideo.getTitle());
            videoResponseDto.setChannelName(savedVideo.getChannelName());
            videoResponseDto.setThumbnailUrl(savedVideo.getThumbnailUrl());
            videoResponseDto.setDateAdded(savedVideo.getDateAdded());

            return videoResponseDto;
        }

        return null;

    }


    public List<VideoResponseDto> getAllVideos() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userRepository.findByUsername(username);

        if(user.isPresent()) {
            List<Video> videos = videoRepository.findByUserId(user.get().getId());
            return videos.stream()
                    .map(this::convertToVideoResponseDto)
                    .collect(Collectors.toList());
        }

        return null;
    }

    public List<VideoResponseDto> sortVideos(String sortBy) {
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
         Optional<User> user = userRepository.findByUsername(username);

        if(user.isPresent()){
            Sort sort;
            if ("title".equalsIgnoreCase(sortBy)) {
                sort = Sort.by(Sort.Direction.ASC, "title");
            } else if ("channel".equalsIgnoreCase(sortBy)) {
                sort = Sort.by(Sort.Direction.ASC, "channelName");
            } else if ("date".equalsIgnoreCase(sortBy)) {
                sort = Sort.by(Sort.Direction.ASC, "dateAdded");
            } else {
                sort = Sort.by(Sort.Direction.ASC, "dateAdded");
            }
            List<Video> videos = videoRepository.findByUserId(user.get().getId(),sort);
            return videos.stream()
                    .map(this::convertToVideoResponseDto)
                    .collect(Collectors.toList());
        }

        return null;

    }

    public List<VideoResponseDto> searchVideos(String query) {
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
         Optional<User> user = userRepository.findByUsername(username);

        if(user.isPresent()){
             List<Video> videosByTitle = videoRepository.findByTitleContainingIgnoreCaseAndUserId(query,user.get().getId());
            List<Video> videosByChannel = videoRepository.findByChannelNameContainingIgnoreCaseAndUserId(query,user.get().getId());
            List<Video> combineList =  videosByTitle.stream().collect(Collectors.toList());
            combineList.addAll(videosByChannel);
            return combineList.stream()
                    .map(this::convertToVideoResponseDto)
                    .collect(Collectors.toList());

        }
        return null;
    }

    private VideoResponseDto convertToVideoResponseDto(Video video) {
        VideoResponseDto videoResponseDto = new VideoResponseDto();
        videoResponseDto.setId(video.getId());
        videoResponseDto.setUrl(video.getUrl());
        videoResponseDto.setTitle(video.getTitle());
        videoResponseDto.setChannelName(video.getChannelName());
        videoResponseDto.setThumbnailUrl(video.getThumbnailUrl());
        videoResponseDto.setDateAdded(video.getDateAdded());
        return videoResponseDto;
    }
}
