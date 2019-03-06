package com.vulpes.velox.repositories;

import com.vulpes.velox.models.BulkProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BulkProductRepository extends CrudRepository<BulkProduct, Long> {


}
