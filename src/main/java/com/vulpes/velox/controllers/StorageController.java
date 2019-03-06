package com.vulpes.velox.controllers;

import com.vulpes.velox.models.BulkProduct;
import com.vulpes.velox.models.IdentifiedProduct;
import com.vulpes.velox.models.Item;
import com.vulpes.velox.models.Shipment;
import com.vulpes.velox.services.IdentifiedProductService;
import com.vulpes.velox.services.ItemService;
import com.vulpes.velox.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StorageController {

  private ProductService productService;
  private IdentifiedProductService identifiedProductService;
  private ItemService itemService;

  @Autowired
  public StorageController(ProductService productService, IdentifiedProductService identifiedProductService, ItemService itemService) {
    this.productService = productService;
    this.identifiedProductService = identifiedProductService;
    this.itemService = itemService;
  }


  @GetMapping("/storage/add")
  public String addProducts(Model model,
                            @ModelAttribute(value = "bulkProductNew") BulkProduct bulkProduct,
                            @ModelAttribute(value = "identifiedProductNew") IdentifiedProduct identifiedProduct,
                            @ModelAttribute(value = "itemNew") Item item,
                            @ModelAttribute(value = "shipmentNew") Shipment shipment) {
    model.addAttribute("identifiedProducts", identifiedProductService.getAll());
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

  @PostMapping("/item/new")
  public String newItem(@RequestParam(value = "identifiedProductToSet") String identifiedProductName,
                        @ModelAttribute(value = "itemNew") Item item) {
    item.setIdentifiedProduct((IdentifiedProduct) productService.getByName(identifiedProductName));
    itemService.save(item);
    return "redirect:/storage/add";
  }

}
