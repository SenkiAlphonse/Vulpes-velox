package com.vulpes.velox.services;

import com.vulpes.velox.exceptions.UnauthorizedException;
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

  public UserServiceImpl() {
  }

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
    return findByEmail(userEmail) != null;
  }

  @Override
  public Boolean isGod(OAuth2Authentication authentication){

    LinkedHashMap<String, Object> properties = (LinkedHashMap<String, Object>) authentication.getUserAuthentication().getDetails();
    String userEmail = properties.get("email").toString();
    User user = userRepo.getByEmail(userEmail);
    if(user!=null) {
      return user.getGod();
    }
    throw new UnauthorizedException("What are you even doing here...");
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
