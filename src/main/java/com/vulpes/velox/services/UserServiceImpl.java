package com.vulpes.velox.services;

import com.vulpes.velox.models.User;
import com.vulpes.velox.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
