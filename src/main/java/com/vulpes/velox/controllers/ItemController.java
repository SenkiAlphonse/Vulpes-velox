package com.vulpes.velox.controllers;

import com.vulpes.velox.exceptions.runtimeexceptions.UnauthorizedException;
import com.vulpes.velox.models.products.IdentifiedProduct;
import com.vulpes.velox.models.Item;
import com.vulpes.velox.services.itemservice.ItemService;
import com.vulpes.velox.services.productservice.ProductService;
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
public class ItemController {

  private ProductService productService;
  private ItemService itemService;
  private UserService userService;

  @Autowired
  public ItemController(ProductService productService,
                        ItemService itemService,
                        UserService userService) {
    this.productService = productService;
    this.itemService = itemService;
    this.userService = userService;
  }

  @GetMapping("/items")
  public String identifiedProducts(Model model) {
    model.addAttribute("items", itemService.getAll());
    return "items";
  }

  @PostMapping("/item/new")
  public String newItem(@RequestParam(value = "identifiedProductToSet") String identifiedProductName,
                        @ModelAttribute(value = "itemNew") Item item,
                        OAuth2Authentication authentication,
                        RedirectAttributes redirectAttributes) {
    if (userService.isUser(authentication)) {
      if(!itemService.getErrorFlashAttributes(identifiedProductName, item, redirectAttributes).isEmpty()) {
        return "redirect:/storage/add#item";
      }

      IdentifiedProduct identifiedProduct = (IdentifiedProduct) productService.getByName(identifiedProductName);

      item.setIdentifiedProduct(identifiedProduct);
      itemService.save(item);

      identifiedProduct.setQuantity((long) itemService.getAllByIdentifiedProduct(identifiedProduct).size());
      productService.update(identifiedProduct);
      itemService.getNewItemFlashAttributes(item, redirectAttributes);
      return "redirect:/storage/add#item";
    } else {
      throw new UnauthorizedException("Unauthorized_request");
    }
  }

}
