package com.vulpes.velox.controllers;

import com.vulpes.velox.models.products.IdentifiedProduct;
import com.vulpes.velox.services.identifiedproductservice.IdentifiedProductService;
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
public class IdentifiedProductController {


  private UserService userService;
  private ProductService productService;
  private IdentifiedProductService identifiedProductService;

  @Autowired
  public IdentifiedProductController(UserService userService, ProductService productService, IdentifiedProductService identifiedProductService) {
    this.userService = userService;
    this.productService = productService;
    this.identifiedProductService = identifiedProductService;
  }

  @PostMapping("/identifiedProduct/new")
  public String identifiedProductNew(
      @ModelAttribute(value = "identifiedProductNew") IdentifiedProduct identifiedProduct,

      OAuth2Authentication authentication,
      RedirectAttributes redirectAttributes,
      Model model) {
    if (userService.isUser(authentication)) {
      if (!identifiedProductService.getErrorFlashAttributes(
          identifiedProduct, redirectAttributes).isEmpty()) {
        return "redirect:/storage/add";
      }

      identifiedProduct.setQuantity((long) 0);
      productService.save(identifiedProduct);
      identifiedProductService.getNewIdentifiedProductFlashAttributes(identifiedProduct, redirectAttributes);
      return "redirect:/storage/add";
    } else {
      model.addAttribute("unauthorizedEmail", userService.getUserEmail(authentication));
      return "unauthorized";
    }
  }
}
