package com.vulpes.velox.services.userservice;

import com.vulpes.velox.exceptions.runtimeexceptions.BadRequestException;
import com.vulpes.velox.exceptions.runtimeexceptions.UnauthorizedException;
import com.vulpes.velox.models.User;
import com.vulpes.velox.repositories.UserRepository;
import com.vulpes.velox.services.methodservice.MethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;
  private MethodService methodService;

  @Autowired
  public UserServiceImpl(UserRepository userRepository,
                         MethodService methodService) {
    this.userRepository = userRepository;
    this.methodService = methodService;
  }

  @Override
  public User findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public void save(User user) {
    userRepository.save(user);
  }

  @Override
  public boolean existsByEmail(String email) {
    return userRepository.findByEmail(email) != null;
  }

  @Override
  public Boolean isUser(OAuth2Authentication authentication) {
    return findByEmail(getUserEmail(authentication)) != null;
  }

  @Override
  public Boolean isAdmin(OAuth2Authentication authentication) {
    String userEmail = getUserEmail(authentication);
    User user = userRepository.findByEmail(userEmail);
    if (user != null) {
      return user.getIsAdmin();
    }
    throw new UnauthorizedException("Access denied, this account is not an admin");
  }

  @Override
  public List<User> getAll(int pageId) {
    return userRepository.findAllByOrderByEmailAsc(
        PageRequest.of(pageId, 10));
  }

  @Override
  public void addUser(User user) {
    if (user == null) {
      throw new BadRequestException("Error creating user");
    }
    userRepository.save(user);

  }

  @Override
  public void deleteUserById(Long id) {
    if (id != null) {
      userRepository.deleteUserById(id);
    }
  }

  @Override
  public User findById(Long id) {
    return userRepository.getById(id);
  }

  @Override
  public Map<String, ?> getErrorFlashAttributes( User user, RedirectAttributes redirectAttributes) {
    String attributeName = "userError";
    String message = "";
    if (user == null) {
      message = "Error creating user.";
    } else if (user.getEmail() == null || user.getEmail().isEmpty()) {
      message = "Enter an e-mail address.";
    } else if (existsByEmail(user.getEmail())) {
      message = "This e-mail address already exists in the database.";
    }
    return methodService.getErrorMessageFlashAttributes(
        message,
        redirectAttributes,
        attributeName);
  }

  @Override
  public String getUserEmail(OAuth2Authentication authentication) {
    LinkedHashMap<String, Object> properties = getAuthDetails(authentication);
    return properties.get("email").toString();
  }

  @Override
  public String getGoogleUserName(OAuth2Authentication authentication) {
    LinkedHashMap<String, Object> properties = getAuthDetails(authentication);
    return properties.get("name").toString();
  }

  @Override
  public LinkedHashMap<String, Object> getAuthDetails(OAuth2Authentication authentication) {
    return (LinkedHashMap<String, Object>) authentication.getUserAuthentication().getDetails();
  }
}
