package com.vulpes.velox.controllers;

import com.vulpes.velox.exceptions.runtimeexceptions.UnauthorizedException;
import com.vulpes.velox.models.products.BulkProduct;
import com.vulpes.velox.services.productservice.ProductService;
import com.vulpes.velox.services.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BulkProductController {

  private UserService userService;
  private ProductService productService;

  @Autowired
  public BulkProductController(UserService userService, ProductService productService) {
    this.userService = userService;
    this.productService = productService;
  }

  @PostMapping("/bulkProduct/new")
  public String bulkProductNew(
      @ModelAttribute(value = "bulkProductNew") BulkProduct bulkProduct,
      OAuth2Authentication authentication) {
    if (userService.isUser(authentication)) {
      bulkProduct.setQuantity((long) 0);
      productService.save(bulkProduct);
      return "redirect:/storage/add";
    }
    throw new UnauthorizedException("You can't do that here :(");
  }


}
