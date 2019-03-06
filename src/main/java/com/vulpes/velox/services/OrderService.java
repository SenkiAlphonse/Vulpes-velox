package com.vulpes.velox.services;

import com.vulpes.velox.models.Order;

import java.util.List;

public interface OrderService {

  List<Order> getAll();

  void save(Order order);

}
