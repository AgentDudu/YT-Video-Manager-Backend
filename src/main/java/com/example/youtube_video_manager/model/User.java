package com.example.youtube_video_manager.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<Video> videos;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<Playlist> playlists;

  // Add necessary constructors, getters, setters
  public User() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<Video> getVideos() {
    return videos;
  }

  public void setVideos(Set<Video> videos) {
    this.videos = videos;
  }

  public Set<Playlist> getPlaylists() {
    return playlists;
  }

  public void setPlaylists(Set<Playlist> playlists) {
    this.playlists = playlists;
  }

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }

}
