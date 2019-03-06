package com.vulpes.velox.services;

import com.vulpes.velox.models.User;

public interface UserService {
  User findByEmail(String email);
}
