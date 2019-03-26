package com.vulpes.velox.controllers;

import com.vulpes.velox.exceptions.runtimeexceptions.UnauthorizedException;
import com.vulpes.velox.models.products.IdentifiedProduct;
import com.vulpes.velox.services.productservice.ProductService;
import com.vulpes.velox.services.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IdentifiedProductController {

  private ProductService productService;
  private UserService userService;

  @Autowired
  public IdentifiedProductController(ProductService productService,
                                     UserService userService) {
    this.productService = productService;
    this.userService = userService;
  }

  @PostMapping("/identifiedProduct/new")
  public String identifiedProductNew(
      @ModelAttribute(value = "identifiedProductNew") IdentifiedProduct identifiedProduct,
      OAuth2Authentication authentication){
    if (userService.isAuthorized(authentication)) {
      identifiedProduct.setQuantity((long)0);
      productService.save(identifiedProduct);
      return "redirect:/storage/add";
    }
    throw new UnauthorizedException("We don't even know you...");
  }

}
