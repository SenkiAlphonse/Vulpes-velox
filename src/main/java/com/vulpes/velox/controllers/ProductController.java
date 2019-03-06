package com.vulpes.velox.controllers;

import com.vulpes.velox.services.BulkProductService;
import com.vulpes.velox.services.ItemService;
import com.vulpes.velox.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

  private ShipmentService shipmentService;
  private ItemService itemService;

  @Autowired
  public ProductController(ShipmentService shipmentService, ItemService itemService) {
    this.shipmentService = shipmentService;
    this.itemService = itemService;
  }

  @GetMapping("/shipments")
  public String shipments(Model model) {
    model.addAttribute("shipments", shipmentService.getAll());
    return "shipments";
  }

  @GetMapping("/items")
  public String identifiedProducts(Model model) {
    model.addAttribute("items", itemService.getAll());
    return "items";
  }


}
