package com.vulpes.velox.controllers;

import com.vulpes.velox.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

  private UserService userService;

  public UserController(UserService userService){
    this.userService = userService;
  }

  @GetMapping("/")
  public String enterApp(){
    return "index";
  }

  @GetMapping("/logout")
  public String logOut(HttpSession session){
    session.invalidate();
    return "redirect:/";
  }
}
