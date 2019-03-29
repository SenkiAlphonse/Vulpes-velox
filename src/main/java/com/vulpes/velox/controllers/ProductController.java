package com.vulpes.velox.controllers;

import com.vulpes.velox.exceptions.runtimeexceptions.UnauthorizedException;
import com.vulpes.velox.services.productservice.ProductService;
import com.vulpes.velox.services.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {

  private UserService userService;
  private ProductService productService;

  @Autowired
  public ProductController(UserService userService, ProductService productService) {
    this.userService = userService;
    this.productService = productService;
  }

  @PostMapping("/deleteAll")
  public String deleteAll(OAuth2Authentication authentication) {
    if (userService.isAdmin(authentication)) {
      productService.deleteAll();
      return "redirect:/storage/add";
    } else {
      throw new UnauthorizedException("Request denied, admin role is required.");
    }
  }

}
