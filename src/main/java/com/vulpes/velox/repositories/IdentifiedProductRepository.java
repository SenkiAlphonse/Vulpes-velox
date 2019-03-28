package com.vulpes.velox.repositories;

import com.vulpes.velox.models.products.IdentifiedProduct;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IdentifiedProductRepository extends CrudRepository<IdentifiedProduct, Long> {

  List<IdentifiedProduct> findAll();

  List<IdentifiedProduct> findAllByNameContaining(String filter);

}
