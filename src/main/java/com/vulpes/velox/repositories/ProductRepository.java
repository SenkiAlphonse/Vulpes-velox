package com.vulpes.velox.repositories;

import com.vulpes.velox.models.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

  boolean existsByName(String name);

  Product findByName(String name);

  List<Product> findAll();

}
