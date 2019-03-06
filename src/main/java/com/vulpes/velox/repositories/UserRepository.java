package com.vulpes.velox.repositories;

import com.vulpes.velox.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
  User getByEmail(String email);
  List<User> findAll();
  void deleteUserByEmail(User user);
}


