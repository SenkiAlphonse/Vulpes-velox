package com.vulpes.velox.controllers;

import com.vulpes.velox.models.BulkProduct;
import com.vulpes.velox.models.IdentifiedProduct;
import com.vulpes.velox.models.Item;
import com.vulpes.velox.models.Shipment;
import com.vulpes.velox.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StorageController {

  private ProductService productService;

  @Autowired
  public StorageController(ProductService productService) {
    this.productService = productService;
  }


  @GetMapping("/storage/add")
  public String addProducts(Model model,
                            @ModelAttribute(value = "bulkProductNew") BulkProduct bulkProduct,
                            @ModelAttribute(value = "identifiedProductNew") IdentifiedProduct identifiedProduct,
                            @ModelAttribute(value = "itemNew") Item item,
                            @ModelAttribute(value = "shipmentNew") Shipment shipment) {
    return "addProducts";
  }

  @PostMapping("/bulkProduct/new")
  public String bulkProductNew(@ModelAttribute(value = "bulkProductNew") BulkProduct bulkProduct) {
    productService.save(bulkProduct);
    return "redirect:/storage/add";
  }

  @PostMapping("/identifiedProduct/new")
  public String identifiedProductNew(
      @ModelAttribute(value = "identifiedProductNew") IdentifiedProduct identifiedProduct) {
    productService.save(identifiedProduct);
    return "redirect:/storage/add";
  }

  @PostMapping("/deleteAll")
  public String deleteAll() {
    productService.deleteAll();
    return "redirect:/storage/add";
  }

}
