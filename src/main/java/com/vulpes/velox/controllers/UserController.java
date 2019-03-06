package com.vulpes.velox.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

  @GetMapping("/home")
  public String home(){
    return "home";
  }

  @GetMapping("/logout")
  public String logOut(HttpSession session){
    session.invalidate();
    return "home";
  }
}
