package com.vulpes.velox.controllers;

import com.vulpes.velox.services.SuperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NewController {

  private SuperService superService;

  @Autowired
  public NewController(SuperService superService) {
    this.superService = superService;
  }

  @GetMapping("/all")
  public String all() {

    for (int i = 0; i < superService.bulkProductService.getAll().size(); i++) {
      System.out.println(superService.bulkProductService.getAll().get(i).getName());
    }

    for (int i = 0; i < superService.identifiedProductService.getAll().size(); i++) {
      System.out.println(superService.identifiedProductService.getAll().get(i).getName());
    }

    for (int i = 0; i < superService.itemService.getAll().size(); i++) {
      System.out.println(superService.itemService.getAll().get(i).getProductNumber());
    }

    System.out.println(superService.userService.findById((long) 1).getId());
    return "new";
  }

}
