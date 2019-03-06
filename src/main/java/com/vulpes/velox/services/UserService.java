package com.vulpes.velox.services;

import com.vulpes.velox.models.User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.List;

public interface UserService {
  User findByEmail(String email);
  List<User>getAll();
  void addUser(User user);
  void deleteUserById(Long id);
  Boolean isAuthorized(OAuth2Authentication authentication);
  Boolean isGod(OAuth2Authentication authentication);
  String getGoogleUserName(OAuth2Authentication authentication);

}
