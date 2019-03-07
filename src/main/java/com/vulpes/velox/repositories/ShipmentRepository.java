package com.vulpes.velox.repositories;

import com.vulpes.velox.models.BulkProduct;
import com.vulpes.velox.models.Shipment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipmentRepository extends CrudRepository<Shipment, Long> {

  List<Shipment> findAll();

  List<Shipment> findAllByBulkProduct(BulkProduct bulkProduct);
}
