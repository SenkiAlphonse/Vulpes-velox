package com.vulpes.velox.controllers;

import com.vulpes.velox.services.BulkProductService;
import com.vulpes.velox.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

  private BulkProductService bulkProductService;
  private ShipmentService shipmentService;

  @Autowired
  public ProductController(BulkProductService bulkProductService, ShipmentService shipmentService) {
    this.bulkProductService = bulkProductService;
    this.shipmentService = shipmentService;
  }

  @GetMapping("/shipments")
  public String shipments(Model model) {
    model.addAttribute("shipments", shipmentService.getAll());
    return "shipments";
  }

}
