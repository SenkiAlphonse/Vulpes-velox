package com.vulpes.velox.repositories;

import com.vulpes.velox.models.Order;
import com.vulpes.velox.models.OrderedProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderedProductRepository extends CrudRepository<OrderedProduct, Long> {

  List<OrderedProduct> findAll();

  List<OrderedProduct> findAllByOrder(Order order);

  List<OrderedProduct> findAllByProductName(String productName);

}
