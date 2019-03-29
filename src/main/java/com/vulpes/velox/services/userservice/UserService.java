package com.vulpes.velox.services.userservice;

import com.vulpes.velox.models.User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

public interface UserService {
  User findByEmail(String email);

  User findById(Long id);

  List<User> getAll(int pageId);

  void addUser(User user);

  void deleteUserById(Long id);

  Boolean isUser(OAuth2Authentication authentication);

  Boolean isAdmin(OAuth2Authentication authentication);

  String getGoogleUserName(OAuth2Authentication authentication);

  boolean userExistsByEmail(String email);

  Map<String, ?> getErrorFlashAttributes(RedirectAttributes redirectAttributes, User user);

  String getUserEmail(OAuth2Authentication authentication);

}
