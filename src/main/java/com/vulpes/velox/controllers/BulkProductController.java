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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BulkProductController {

  private ProductService productService;
  private UserService userService;

  @Autowired
  public BulkProductController(ProductService productService,
                               UserService userService) {
    this.productService = productService;
    this.userService = userService;
  }

  @PostMapping("/bulkProduct/new")
  public String bulkProductNew(
      @ModelAttribute(value = "bulkProductNew") BulkProduct bulkProduct,
      OAuth2Authentication authentication,
      RedirectAttributes redirectAttributes){
    if (userService.isAuthorized(authentication)){
      bulkProduct.setQuantity((long) 0);
      productService.save(bulkProduct);

      redirectAttributes.addFlashAttribute("savedBulkProduct", true);
      redirectAttributes.addFlashAttribute("bulkProductName", bulkProduct.getName());

      return "redirect:/storage/add";
    }
    throw new UnauthorizedException("You can't do that here :(");
  }

}
