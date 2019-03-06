package com.vulpes.velox.controllers;

import com.vulpes.velox.models.User;
import com.vulpes.velox.services.UserService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.LinkedHashMap;

@Controller
public class UserController {

  private UserService userService;

  public UserController(UserService userService){
    this.userService = userService;
  }

  @GetMapping("/")
  public String enterApp(OAuth2Authentication authentication){
    return "index";
  }

  @GetMapping("/logout")
  public String logOut(HttpSession session){
    session.invalidate();
    return "redirect:/";
  }

  @GetMapping("/users")
  public  String showUsers(Model model){
    //implement users to model logic
    model.addAttribute("users", userService.getAll());
    model.addAttribute("newuser", new User());
    return "users";
  }

  @PostMapping("users")
  public String addUser(@ModelAttribute(name = "newuser") User newUser){
    if (newUser!=null){
      userService.addUser(newUser);
    }
    return "redirect:/users";
  }

  @PostMapping("users/delete/{email}")
  public String deleteUser(@PathParam(value = "email")String email){
    userService.deleteUserByEmail(email);
    return "redirect:/users";
  }

}
