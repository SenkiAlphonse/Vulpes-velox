package com.vulpes.velox.services.orderservice;

import com.vulpes.velox.controllers.restcontrollers.RESTexception.OrderExceptions;
import com.vulpes.velox.models.Order;

import java.util.List;

public interface OrderService {

  List<Order> getAll();

  void save(Order order);

  Order getByName(String name);

  boolean existsByName(String name);

  Order findById(Long id) throws OrderExceptions;
}
