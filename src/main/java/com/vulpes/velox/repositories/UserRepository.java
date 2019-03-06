package com.vulpes.velox.repositories;

import com.vulpes.velox.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
  User getByEmail(String email);
}
