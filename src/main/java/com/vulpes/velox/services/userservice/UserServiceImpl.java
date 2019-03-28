package com.vulpes.velox.services.userservice;

import com.vulpes.velox.exceptions.runtimeexceptions.BadEmailException;
import com.vulpes.velox.exceptions.runtimeexceptions.BadRequestException;
import com.vulpes.velox.exceptions.runtimeexceptions.UnauthorizedException;
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
  public Boolean isUser(OAuth2Authentication authentication){
    LinkedHashMap<String, Object> properties = (LinkedHashMap<String, Object>) authentication.getUserAuthentication().getDetails();
    String userEmail = properties.get("email").toString();
    return findByEmail(userEmail) != null;
  }

  @Override
  public Boolean isAdmin(OAuth2Authentication authentication){
    LinkedHashMap<String, Object> properties = (LinkedHashMap<String, Object>) authentication.getUserAuthentication().getDetails();
    String userEmail = properties.get("email").toString();
    User user = userRepo.getByEmail(userEmail);
    if(user!=null) {
      return user.getIsAdmin();
    }
    throw new UnauthorizedException("Access denied, this account is not an admin");
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
    if(user==null){
      throw new BadRequestException("Error creating user");
    }
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
