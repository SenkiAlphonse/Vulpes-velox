package com.vulpes.velox.controllers;

import com.vulpes.velox.exceptions.runtimeexceptions.UnauthorizedException;
import com.vulpes.velox.models.User;
import com.vulpes.velox.services.userservice.UserService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {

  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @ModelAttribute(value = "username")
  public String welcomeUser() {
    return "Stranger";
  }

  @GetMapping("/")
  public String enterApp(Model model, OAuth2Authentication authentication) {
    if (authentication != null && userService.isAuthorized(authentication)) {
      model.addAttribute("username", userService.getGoogleUserName(authentication));
    }
    return "index";
  }

  @GetMapping("/logout")
  public String logOut(HttpSession session) {
    session.invalidate();
    return "redirect:/";
  }

  @GetMapping("/users")
  public String showUsers(Model model,
                          OAuth2Authentication authentication,
                          @RequestParam(value = "pageId", required = false, defaultValue = "0") int pageId,
                          @ModelAttribute(name = "newuser") User newUser) {
    if (userService.isAdmin(authentication)) {
      List<User> myPage = userService.getAll(pageId);
      List<User> peekPage = userService.getAll(pageId + 1);

      model.addAttribute("users", myPage);
      model.addAttribute("pageid", pageId);
      model.addAttribute("islastpage", peekPage.size() == 0);
      return "users";
    }
    throw new UnauthorizedException("Only Gods can tamper with users");
  }

  @PostMapping("/users")
  public String addUser(@ModelAttribute(name = "newuser") User newUser, OAuth2Authentication authentication) {
    if (userService.isAdmin(authentication)) {
      userService.addUser(newUser);
      return "redirect:/users";
    }
    throw new UnauthorizedException("Only Gods can tamper with users");
  }

  @PostMapping("/users/delete/{id}")
  public String deleteUser(@PathVariable(value = "id") Long id, OAuth2Authentication authentication) {
    if (userService.isAdmin(authentication)) {
      userService.deleteUserById(id);
      return "redirect:/users";
    }
    throw new UnauthorizedException("Only Gods can tamper with users");
  }

  @PostMapping("/users/update/{id}")
  public String updateUser(@PathVariable(value = "id") Long id, OAuth2Authentication authentication) {
    if (userService.isAuthorized(authentication) && userService.isAdmin(authentication)) {
      User updateUser = userService.findById(id);
      updateUser.setGod(!updateUser.getGod());
      userService.addUser(updateUser);
      return "redirect:/users";
    }
    throw new UnauthorizedException("Only Gods can tamper with users");
  }

}
