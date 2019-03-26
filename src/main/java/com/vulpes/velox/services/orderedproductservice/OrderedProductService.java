package com.vulpes.velox.services.orderedproductservice;

import com.vulpes.velox.models.Order;
import com.vulpes.velox.models.OrderedProduct;

import java.util.List;

public interface OrderedProductService {

  void save(OrderedProduct orderedProduct);

  List<OrderedProduct> getAll();

  List<OrderedProduct> getAllByOrder(Order order);

  List<OrderedProduct> getAllByProductName(String productName);
}
