package com.example.youtube_video_manager.controller;

import com.example.youtube_video_manager.dto.UserDto;
import com.example.youtube_video_manager.model.User;
import com.example.youtube_video_manager.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final UserService userService;
  private final BCryptPasswordEncoder passwordEncoder;

  public AuthController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
    if (userService.checkIfUsernameExists(userDto.getUsername())) {
      return new ResponseEntity<>("User with this username already exist", HttpStatus.CONFLICT);
    }

    User user = new User();
    user.setUsername(userDto.getUsername());
    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    userService.save(user);
    return new ResponseEntity<>("User register successfully", HttpStatus.CREATED);
  }
}
