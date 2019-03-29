package com.vulpes.velox.controllers;

import com.vulpes.velox.exceptions.runtimeexceptions.UnauthorizedException;
import com.vulpes.velox.models.products.BulkProduct;
import com.vulpes.velox.models.Shipment;
import com.vulpes.velox.services.productservice.ProductService;
import com.vulpes.velox.services.shipmentservice.ShipmentService;
import com.vulpes.velox.services.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ShipmentController {


  private UserService userService;
  private ShipmentService shipmentService;
  private ProductService productService;

  @Autowired
  public ShipmentController(UserService userService, ShipmentService shipmentService, ProductService productService) {
    this.userService = userService;
    this.shipmentService = shipmentService;
    this.productService = productService;
  }

  @GetMapping("/shipments")
  public String shipments(Model model) {
    model.addAttribute("shipments", shipmentService.getAll());
    return "shipments";
  }

  @PostMapping("/shipment/new")
  public String shipmentNew(@RequestParam(
      value = "bulkProductToSet", required = false) String bulkProductName,
                            @RequestParam(value = "arrivalToSet") String arrivalDate,
                            @RequestParam(value = "bestBeforeToSet") String bestBeforeDate,
                            @ModelAttribute(value = "shipmentNew") Shipment shipment,
                            OAuth2Authentication authentication,
                            RedirectAttributes redirectAttributes,
                            Model model) {
    if (userService.isUser(authentication)) {
      if (!shipmentService.getErrorFlashAttributes(
          bulkProductName, arrivalDate, bestBeforeDate, shipment, redirectAttributes).isEmpty()) {
        return "redirect:/storage/add#shipment";
      }
      shipment.setArrival(shipmentService.getLocalDateFromDateString(arrivalDate));
      shipment.setBestBefore(shipmentService.getLocalDateFromDateString(bestBeforeDate));
      BulkProduct bulkProduct = (BulkProduct) productService.getByName(bulkProductName);

      shipment.setBulkProduct(bulkProduct);
      shipmentService.save(shipment);

      bulkProduct.setQuantity(bulkProduct.getQuantity() + shipment.getQuantity());
      productService.update(bulkProduct);

      shipmentService.getNewShipmentFlashAttributes(shipment, redirectAttributes);
      return "redirect:/storage/add#shipment";
    } else {
      model.addAttribute("unauthorizedEmail", userService.getUserEmail(authentication));
      return "unauthorized";
    }
  }

}
