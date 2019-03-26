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

  private ProductService productService;
  private UserService userService;

  @Autowired
  public ProductController(ProductService productService,
                           UserService userService) {
    this.productService = productService;
    this.userService = userService;
  }

  @PostMapping("/deleteAll")
  public String deleteAll(OAuth2Authentication authentication) {
    if (userService.isGod(authentication)){
    productService.deleteAll();
    return "redirect:/storage/add";
    }
    else {
      throw new UnauthorizedException("Nice try but nope.");
    }
  }

}
