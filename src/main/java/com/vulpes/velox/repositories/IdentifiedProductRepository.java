package com.vulpes.velox.repositories;

import com.vulpes.velox.models.IdentifiedProduct;
import org.springframework.data.repository.CrudRepository;

public interface IdentifiedProductRepository extends CrudRepository<IdentifiedProduct, Long> {
}
