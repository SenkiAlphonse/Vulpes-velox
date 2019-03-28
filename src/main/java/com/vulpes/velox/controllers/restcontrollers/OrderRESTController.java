package com.vulpes.velox.controllers.restcontrollers;

import com.vulpes.velox.controllers.restcontrollers.RESTexception.OrderExceptions;
import com.vulpes.velox.models.Order;
import com.vulpes.velox.services.orderedproductservice.OrderedProductService;
import com.vulpes.velox.services.orderservice.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/order")
public class OrderRESTController {

  private OrderService orderService;
  private OrderedProductService orderedProductService;

  @Autowired
  public OrderRESTController(OrderService orderService, OrderedProductService orderedProductService) {
    this.orderService = orderService;
    this.orderedProductService = orderedProductService;
  }

  @GetMapping("/{id}")
  public Order order(@PathVariable Long id) throws OrderExceptions {
    return orderService.findById(id);
  }

  @PostMapping("/new")
  public void saveOrder(Order order) {
    orderService.save(order);
  }
}
