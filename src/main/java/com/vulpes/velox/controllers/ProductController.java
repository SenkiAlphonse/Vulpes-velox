package com.vulpes.velox.controllers;

import com.vulpes.velox.services.BulkProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

  private BulkProductService bulkProductService;

  @Autowired
  public ProductController(BulkProductService bulkProductService) {
    this.bulkProductService = bulkProductService;
  }

  @GetMapping("/bulk-products")
  public String bulkProducts(Model model) {

    model.addAttribute("bulkProducts", bulkProductService.getAll());

    return "bulkProducts";
  }


}
