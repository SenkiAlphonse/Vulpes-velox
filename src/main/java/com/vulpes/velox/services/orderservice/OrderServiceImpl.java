package com.vulpes.velox.services.orderservice;

import com.vulpes.velox.models.Order;
import com.vulpes.velox.repositories.OrderRepository;
import com.vulpes.velox.services.methodservice.MethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

  private OrderRepository orderRepository;
  private MethodService methodService;

  @Autowired
  public OrderServiceImpl(OrderRepository orderRepository,
                          MethodService methodService) {
    this.orderRepository = orderRepository;
    this.methodService = methodService;
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
  public Map<String, ?> getErrorFlashAttributes(Order order,
                                                RedirectAttributes redirectAttributes) {
    String attributeName = "orderError";
    String message = "";
    if (order.getName() == null) {
      message = "Enter order name.";
    }
    if (order.getName().isEmpty()) {
      message = "Empty order name.";
    }
    if (existsByName(order.getName())) {
      return methodService.getErrorMessageFlashAttributes(
          "Order name already exists.",
          redirectAttributes,
          attributeName);
    }
    return redirectAttributes.getFlashAttributes();
  }

}
