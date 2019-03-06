package com.vulpes.velox.services;

import com.vulpes.velox.models.User;
import com.vulpes.velox.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepo;

  @Autowired
  public UserServiceImpl(UserRepository userRepository){
    this.userRepo = userRepository;
  }

  @Override
  public User findByEmail(String email) {
    return userRepo.getByEmail(email);
  }

  @Override
  public Boolean isAuthorized(OAuth2Authentication authentication){
    LinkedHashMap<String, Object> properties = (LinkedHashMap<String, Object>) authentication.getUserAuthentication().getDetails();
    String userEmail = properties.get("email").toString();
    if (findByEmail(userEmail)!=null) {
      return true;
    }
    else {
      return false;
    }
  }

  @Override
  public Boolean isGod(OAuth2Authentication authentication) {
    LinkedHashMap<String, Object> properties = (LinkedHashMap<String, Object>) authentication.getUserAuthentication().getDetails();
    String userEmail = properties.get("email").toString();
    if (System.getenv("GOD_USER").equals(userEmail)) {
      return true;
    }
    else {
      return false;
    }
  }

  @Override
  public String getGoogleUserName(OAuth2Authentication authentication) {
    LinkedHashMap<String, Object> properties = (LinkedHashMap<String, Object>) authentication.getUserAuthentication().getDetails();
    return properties.get("name").toString();
  }

  @Override
  public List<User> getAll(int pageId) {
    return userRepo.findAllByOrderByEmailAsc(PageRequest.of(pageId, 10));
  }

  @Override
  public void addUser(User user) {
    userRepo.save(user);
  }

  @Override
  public void deleteUserById(Long id) {
    if(id!=null){
      userRepo.deleteUserById(id);
    }
  }

  @Override
  public User findById(Long id) {
    return userRepo.getById(id);
  }
}
