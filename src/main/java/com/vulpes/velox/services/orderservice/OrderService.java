package com.vulpes.velox.services.orderservice;

import com.vulpes.velox.models.Order;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

public interface OrderService {

  List<Order> getAll();

  void save(Order order);

  Order getByName(String name);

  boolean existsByName(String name);

  Map<String, ?> getErrorFlashAttributes(Order order, RedirectAttributes redirectAttributes);

  Map<String, ?> getNewBulkProductFlashAttributes(Order order, RedirectAttributes redirectAttributes);
}
