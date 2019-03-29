package com.vulpes.velox.controllers;

import com.vulpes.velox.exceptions.runtimeexceptions.UnauthorizedException;
import com.vulpes.velox.models.products.BulkProduct;
import com.vulpes.velox.services.bulkproductservice.BulkProductService;
import com.vulpes.velox.services.productservice.ProductService;
import com.vulpes.velox.services.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BulkProductController {

  private UserService userService;
  private ProductService productService;
  private BulkProductService bulkProductService;

  @Autowired
  public BulkProductController(UserService userService, ProductService productService, BulkProductService bulkProductService) {
    this.userService = userService;
    this.productService = productService;
    this.bulkProductService = bulkProductService;
  }

  @PostMapping("/bulkProduct/new")
  public String bulkProductNew(
      @ModelAttribute(value = "bulkProductNew") BulkProduct bulkProduct,
      OAuth2Authentication authentication,
      RedirectAttributes redirectAttributes,
      Model model) {
    if (userService.isUser(authentication)) {
      if (!bulkProductService.getErrorFlashAttributes(
          bulkProduct, redirectAttributes).isEmpty()) {
        return "redirect:/storage/add";
      }
      bulkProduct.setQuantity((long) 0);
      productService.save(bulkProduct);
      bulkProductService.getNewBulkProductFlashAttributes(bulkProduct, redirectAttributes);
      return "redirect:/storage/add";
    } else {
      model.addAttribute("unauthorizedEmail", userService.getUserEmail(authentication));
      return "unauthorized";
    }
  }
}
