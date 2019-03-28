package com.vulpes.velox.services.orderservice;

import com.vulpes.velox.controllers.restcontrollers.RESTexception.OrderExceptions;
import com.vulpes.velox.models.Order;
import com.vulpes.velox.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

  private OrderRepository orderRepository;

  @Autowired
  public OrderServiceImpl(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @Override
  public List<Order> getAll() {
    return orderRepository.findAll();
  }

  @Override
  public void save(Order order) {
    if (!existsByName(order.getName())) {
      orderRepository.save(order);
    }
  }

  @Override
  public Order getByName(String name) {
    return orderRepository.findByName(name);
  }

  @Override
  public boolean existsByName(String name) {
    return orderRepository.existsByName(name);
  }

  @Override
  public Order findById(Long id) throws OrderExceptions {
    return orderRepository.findById(id).orElseThrow(() -> new OrderExceptions("Order did not find with" + id + " id"));
  }
}
