package com.vulpes.velox.controllers;

import com.vulpes.velox.models.Order;
import com.vulpes.velox.services.orderservice.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

@Controller
public class OrderController {

  private OrderService orderService;

  @Autowired
  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping("/order")
  public String order(@ModelAttribute(value = "orderNew") Order order) {
    return "order";
  }

  @PostMapping("/order/new")
  public String saveOrder(@ModelAttribute(value = "orderNew") Order order,
                          RedirectAttributes redirectAttributes) {
    order.setDate(new Date());
    orderService.save(order);

    redirectAttributes.addAttribute("orderName", order.getName());
    return "redirect:/order/{orderName}";
  }

}
