package com.vulpes.velox.services.userservice;

import com.vulpes.velox.models.User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface UserService {
  User getByEmail(String email);

  void save(User user);

  User getById(Long id);

  List<User> getAllForPage(int pageId);

  void addUser(User user);

  void deleteUserById(Long id);

  Boolean isUser(OAuth2Authentication authentication);

  Boolean isAdmin(OAuth2Authentication authentication);

  String getGoogleUserName(OAuth2Authentication authentication);

  boolean existsByEmail(String email);

  Map<String, ?> getErrorFlashAttributes(User user, RedirectAttributes redirectAttributes);

  String getUserEmail(OAuth2Authentication authentication);

  LinkedHashMap<String, Object> getAuthDetails(OAuth2Authentication authentication);

}
