package com.vulpes.velox.repositories;

import com.vulpes.velox.models.products.BulkProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BulkProductRepository extends CrudRepository<BulkProduct, Long> {

  List<BulkProduct> findAll();

  boolean existsByName(String name);

}
