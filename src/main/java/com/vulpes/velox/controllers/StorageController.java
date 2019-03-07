package com.vulpes.velox.controllers;

import com.vulpes.velox.exceptions.UnauthorizedException;
import com.vulpes.velox.models.BulkProduct;
import com.vulpes.velox.models.IdentifiedProduct;
import com.vulpes.velox.models.Item;
import com.vulpes.velox.models.Shipment;
import com.vulpes.velox.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;

@Controller
public class StorageController {

  private ProductService productService;
  private IdentifiedProductService identifiedProductService;
  private ItemService itemService;
  private BulkProductService bulkProductService;
  private ShipmentService shipmentService;
  private UserService userService;

  @Autowired
  public StorageController(ProductService productService,
                           IdentifiedProductService identifiedProductService,
                           ItemService itemService,
                           BulkProductService bulkProductService,
                           ShipmentService shipmentService,
                           UserService userService) {
    this.productService = productService;
    this.identifiedProductService = identifiedProductService;
    this.itemService = itemService;
    this.bulkProductService = bulkProductService;
    this.shipmentService = shipmentService;
    this.userService = userService;
  }

  @GetMapping("/storage/add")
  public String addProducts(Model model,
                            @ModelAttribute(value = "bulkProductNew") BulkProduct bulkProduct,
                            @ModelAttribute(value = "identifiedProductNew") IdentifiedProduct identifiedProduct,
                            @ModelAttribute(value = "itemNew") Item item,
                            @ModelAttribute(value = "shipmentNew") Shipment shipment,
                            OAuth2Authentication authentication) {
    if (userService.isAuthorized(authentication)){
    model.addAttribute("identifiedProducts", identifiedProductService.getAll());
    model.addAttribute("bulkProducts", bulkProductService.getAll());
    return "addProducts";}
    else {
      throw new UnauthorizedException("Unauthorized");
    }
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
  public String deleteAll(OAuth2Authentication authentication) {
    if (userService.isAuthorized(authentication) && userService.isGod(authentication)){
    productService.deleteAll();
    return "redirect:/storage/add";
    }
    else {
      throw new UnauthorizedException("Nice try but nope.");
    }
  }

  @PostMapping("/item/new")
  public String newItem(@RequestParam(value = "identifiedProductToSet") String identifiedProductName,
                        @ModelAttribute(value = "itemNew") Item item,
                        OAuth2Authentication authentication) {
    if (userService.isAuthorized(authentication)) {
      IdentifiedProduct identifiedProduct = (IdentifiedProduct) productService.getByName(identifiedProductName);

      item.setIdentifiedProduct(identifiedProduct);
      itemService.save(item);

      identifiedProduct.setQuantity((long) itemService.getAllByIdentifiedProduct(identifiedProduct).size());
      productService.save(identifiedProduct);
      return "redirect:/storage/add";
    }
    else {
      throw new UnauthorizedException("Unauthorized_request");
    }
  }

  @PostMapping("/shipment/new")
  public String shipmentNew(@RequestParam(value = "bulkProductToSet") String bulkProductName,
                            @RequestParam(value = "arrivalToSet") String arrivalDate,
                            @RequestParam(value = "bestBeforeToSet") String bestBeforeDate,
                            @ModelAttribute(value = "shipmentNew") Shipment shipment) {

    shipment.setArrival(shipmentService.getLocalDateFromDateString(arrivalDate));
    shipment.setBestBefore(shipmentService.getLocalDateFromDateString(bestBeforeDate));

    BulkProduct bulkProduct = (BulkProduct) productService.getByName(bulkProductName);

    shipment.setBulkProduct(bulkProduct);
    shipmentService.save(shipment);

    bulkProduct.setQuantity((long) shipmentService.getAllByBulkProduct(bulkProduct).size());
    productService.save(bulkProduct);
    return "redirect:/storage/add";
  }
}
