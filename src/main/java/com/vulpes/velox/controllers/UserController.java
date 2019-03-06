package com.vulpes.velox.controllers;

import com.vulpes.velox.exceptions.UnauthorizedException;
import com.vulpes.velox.models.User;
import com.vulpes.velox.services.UserService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

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
  public  String showUsers(Model model, OAuth2Authentication authentication){
    if (userService.isAuthorized(authentication) && userService.isGod(authentication)){
    model.addAttribute("users", userService.getAll());
    model.addAttribute("newuser", new User());
    return "users";}
    else {
      throw new UnauthorizedException("Only Gods can tamper with users");
    }
  }

  @PostMapping("/users")
  public String addUser(@ModelAttribute(name = "newuser") User newUser, OAuth2Authentication authentication){
    if (userService.isAuthorized(authentication) && userService.isGod(authentication)) {
      if (newUser != null) {
        userService.addUser(newUser);
      }
      return "redirect:/users";
    }
    else {
      throw new UnauthorizedException("Only Gods can tamper with users");
    }
  }

  @PostMapping("/users/delete/{id}")
  public String deleteUser(@PathVariable(value = "id")Long id, OAuth2Authentication authentication){
    if (userService.isAuthorized(authentication) && userService.isGod(authentication)) {
      userService.deleteUserById(id);
      return "redirect:/users";
    }
    else {
      throw new UnauthorizedException("Only Gods can tamper with users");
    }
  }

  @PostMapping("/users/update/{id}")
  public String updateUser(@PathVariable(value = "id")Long id, OAuth2Authentication authentication){
    if (userService.isAuthorized(authentication) && userService.isGod(authentication)){
      User updateUser = userService.findById(id);
      updateUser.setGod(!updateUser.getGod());
      userService.addUser(updateUser);
      return "redirect:/users";
    }
    else {
      throw new UnauthorizedException("Only Gods can tamper with users");
    }
  }

}
