package com.vulpes.velox.controllers;

import com.vulpes.velox.models.Shipment;
import com.vulpes.velox.models.User;
import com.vulpes.velox.services.bulkproductservice.BulkProductService;
import com.vulpes.velox.services.productservice.ProductService;
import com.vulpes.velox.services.shipmentservice.ShipmentService;
import com.vulpes.velox.services.userservice.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = UserController.class, secure = false)
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  private Map<String, Boolean> errorFlashAttributes;

  @Before
  public void setup() {
    errorFlashAttributes = new HashMap<>();
    errorFlashAttributes.put("bulkProductError", true);
  }

  @Test
  public void welcome() throws Exception {
    mockMvc.perform(get("/"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(model().attribute("username", "Stranger"))
        .andExpect(view().name("index"));
  }

  @Test
  public void logout() throws Exception {
    mockMvc.perform(get("/logout"))
        .andDo(print())
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/"))
        .andExpect(view().name("redirect:/"));
  }

  @Test
  public void usersWithPageId() throws Exception {
    when(userService.isUser(isNull())).thenReturn(true);
    when(userService.getAll(5)).thenReturn(Collections.emptyList());
    when(userService.getAll(6)).thenReturn(Collections.emptyList());

    mockMvc.perform(get("/users")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("id", "1")
        .param("pageId", "5")
    )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(model().attribute("users", Collections.emptyList()))
        .andExpect(model().attribute("pageid", 5))
        .andExpect(model().attribute("islastpage", true))
        .andExpect(view().name("users"));

    verify(userService, times(1)).isUser(isNull());
    verify(userService, times(1)).getAll(5);
    verify(userService, times(1)).getAll(6);
    verifyNoMoreInteractions(userService);
  }

  @Test
  public void usersWithoutPageId() throws Exception {
    when(userService.isUser(isNull())).thenReturn(true);
    when(userService.getAll(0)).thenReturn(Collections.emptyList());
    when(userService.getAll(1)).thenReturn(Collections.emptyList());

    mockMvc.perform(get("/users")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("id", "1")
    )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(model().attribute("users", Collections.emptyList()))
        .andExpect(model().attribute("pageid", 0))
        .andExpect(model().attribute("islastpage", true))
        .andExpect(view().name("users"));

    verify(userService, times(1)).isUser(isNull());
    verify(userService, times(1)).getAll(0);
    verify(userService, times(1)).getAll(1);
    verifyNoMoreInteractions(userService);
  }

  @Test
  public void usersNotAuthenticated() throws Exception {
    when(userService.isUser(isNull())).thenReturn(false);
    when(userService.getUserEmail(isNull())).thenReturn("email");

    mockMvc.perform(get("/users")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("id", "1")
        .param("pageId", "5")
    )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(model().attributeDoesNotExist("users"))
        .andExpect(model().attributeDoesNotExist("pageid"))
        .andExpect(model().attributeDoesNotExist("islastpage"))
        .andExpect(model().attribute("unauthorizedEmail", "email"))
        .andExpect(view().name("unauthorized"));

    verify(userService, times(1)).isUser(isNull());
    verify(userService, times(1)).getUserEmail(isNull());
    verifyNoMoreInteractions(userService);
  }

  @Test
  public void usersNew() throws Exception {
    when(userService.isAdmin(isNull())).thenReturn(true);
    when((Object) userService.getErrorFlashAttributes(
        notNull(), notNull())).thenReturn(Collections.emptyMap());

    mockMvc.perform(post("/users")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("id", "1")
        .param("email", "Email")
        .param("name", "Name")
    )
        .andDo(print())
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/users"))
        .andExpect(view().name("redirect:/users"));

    ArgumentCaptor<User> userArgument = ArgumentCaptor.forClass(User.class);
    verify(userService, times(1)).isAdmin(isNull());
    verify(userService, times(1)).addUser(userArgument.capture());
    verify(userService, times(1))
        .getErrorFlashAttributes(any(RedirectAttributes.class), any(User.class));
    verifyNoMoreInteractions(userService);

    User userArgumentValue = userArgument.getValue();
    assertThat(userArgumentValue.getId(), is((long) 1));
    assertThat(userArgumentValue.getEmail(), is("Email"));
    assertThat(userArgumentValue.getName(), is("Name"));
  }

  @Test
  public void usersNewEmailTaken() throws Exception {
    when(userService.isAdmin(isNull())).thenReturn(true);
    when((Object) userService.getErrorFlashAttributes(
        notNull(), notNull())).thenReturn(errorFlashAttributes);

    mockMvc.perform(post("/users")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("id", "1")
        .param("email", "Taken")
        .param("name", "Name")
    )
        .andDo(print())
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/users#adduser"))
        .andExpect(view().name("redirect:/users#adduser"));

    verify(userService, times(1)).isAdmin(isNull());
    verify(userService, times(1))
        .getErrorFlashAttributes(any(RedirectAttributes.class), any(User.class));
    verifyNoMoreInteractions(userService);
  }

  @Test
  public void usersNewUnauthorized() throws Exception {
    when(userService.isAdmin(isNull())).thenReturn(false);

    mockMvc.perform(post("/users")
    )
        .andDo(print())
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.status", is("UNAUTHORIZED")))
        .andExpect(jsonPath("$.message", is(
            "Request denied, admin role is required to add users.")))
        .andExpect(jsonPath("$.path", is("/users")))
        .andExpect(jsonPath("$.timeStamp", containsString("")));

    verify(userService, times(1)).isAdmin(isNull());
    verifyNoMoreInteractions(userService);
  }












}
