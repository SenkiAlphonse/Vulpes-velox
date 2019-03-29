package com.vulpes.velox.controllers;

import com.vulpes.velox.exceptions.runtimeexceptions.UnauthorizedException;
import com.vulpes.velox.models.products.BulkProduct;
import com.vulpes.velox.models.products.IdentifiedProduct;
import com.vulpes.velox.models.Item;
import com.vulpes.velox.models.Shipment;
import com.vulpes.velox.services.bulkproductservice.BulkProductService;
import com.vulpes.velox.services.identifiedproductservice.IdentifiedProductService;
import com.vulpes.velox.services.itemservice.ItemService;
import com.vulpes.velox.services.productservice.ProductService;
import com.vulpes.velox.services.shipmentservice.ShipmentService;
import com.vulpes.velox.services.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class StorageController {

  private IdentifiedProductService identifiedProductService;
  private BulkProductService bulkProductService;
  private UserService userService;

  @Autowired
  public StorageController(IdentifiedProductService identifiedProductService,
                           BulkProductService bulkProductService,
                           UserService userService) {
    this.identifiedProductService = identifiedProductService;
    this.bulkProductService = bulkProductService;
    this.userService = userService;
  }

  @GetMapping("/storage/add")
  public String addProducts(@Valid Model model,
                            @ModelAttribute(value = "bulkProductNew") BulkProduct bulkProduct,
                            @ModelAttribute(value = "identifiedProductNew") IdentifiedProduct identifiedProduct,
                            @ModelAttribute(value = "itemNew") Item item,
                            @ModelAttribute(value = "shipmentNew") Shipment shipment,
                            @RequestParam(value = "filterProducts", required = false) String filter,
                            OAuth2Authentication authentication) {
    if (userService.isUser(authentication)) {
      if (filter != null && !filter.isEmpty()) {
        model.addAttribute(
            "identifiedProductsFiltered",
            identifiedProductService.getAllFilteredBy(filter));
        model.addAttribute(
            "bulkProductsFiltered",
            bulkProductService.getAllFilteredBy(filter));
      } else {
        model.addAttribute("identifiedProductsFiltered", identifiedProductService.getAll());
        model.addAttribute("bulkProductsFiltered", bulkProductService.getAll());
      }
      return "addProducts";
    } else {
      model.addAttribute("unauthorizedEmail", userService.getUserEmail(authentication));
      return "unauthorized";
    }
  }


}
