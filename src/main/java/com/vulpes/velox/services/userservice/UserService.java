package com.vulpes.velox.services.userservice;

import com.vulpes.velox.models.User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.List;

public interface UserService {
  User findByEmail(String email);
  User findById(Long id);
  List<User>getAll(int pageId);
  void addUser(User user);
  void deleteUserById(Long id);
  Boolean isAuthorized(OAuth2Authentication authentication);
  Boolean isAdmin(OAuth2Authentication authentication);
  String getGoogleUserName(OAuth2Authentication authentication);

}
