package com.example.youtube_video_manager.service;

import com.example.youtube_video_manager.model.User;
import com.example.youtube_video_manager.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Optional<User> user = userRepository.findByUsername(username);
    if (user.isPresent()) {
      User user1 = user.get();
      return new org.springframework.security.core.userdetails.User(user1.getUsername(), user1.getPassword(),
          new ArrayList<>());
    } else {
      throw new UsernameNotFoundException("User not found with username: " + username);
    }
  }

  public User save(User user) {
    return userRepository.save(user);
  }

  public Optional<User> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public boolean checkIfUsernameExists(String username) {
    Optional<User> user = userRepository.findByUsername(username);
    return user.isPresent();
  }

}
