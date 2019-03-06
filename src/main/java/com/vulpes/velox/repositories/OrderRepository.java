package com.vulpes.velox.repositories;

import com.vulpes.velox.models.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

  List<Order> findAll();

  Order findByName(String name);

  boolean existsByName(String name);

}
