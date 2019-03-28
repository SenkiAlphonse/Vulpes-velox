package com.vulpes.velox.services.orderservice;

import com.vulpes.velox.models.Order;
import com.vulpes.velox.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

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
  public Map<String, ?> getErrorFlashAttributes(Order order, RedirectAttributes redirectAttributes) {
    if(order.getName() == null) {
      return getErrorMessageFlashAttributes("Enter bulk product name.", redirectAttributes);
    }
    if(order.getName().isEmpty()) {
      return getErrorMessageFlashAttributes("Empty bulk product name.", redirectAttributes);
    }
    if(orderRepository.existsByName(order.getName())) {
      return getErrorMessageFlashAttributes("Product name already exists.", redirectAttributes);
    }
    return redirectAttributes.getFlashAttributes();
  }

  private Map<String, ?> getErrorMessageFlashAttributes(String message,
                                                        RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("bulkProductError", true);
    redirectAttributes.addFlashAttribute("errorMessage", message);
    return redirectAttributes.getFlashAttributes();
  }

}
