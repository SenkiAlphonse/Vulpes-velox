package com.vulpes.velox.repositories;

import com.vulpes.velox.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.Id;
import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

  List<User> findAllByOrderByEmailAsc(Pageable pageable);

  User findByEmail(String email);
  User getById(Long id);
  List<User> findAll();
  @Transactional
  void deleteUserById(Long id);
}


