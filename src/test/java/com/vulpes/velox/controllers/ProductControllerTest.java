package com.vulpes.velox.controllers;

import com.vulpes.velox.services.productservice.ProductService;
import com.vulpes.velox.services.userservice.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = ProductController.class, secure = false)
public class ProductControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ProductService productService;
  @MockBean
  private UserService userService;

  @Before
  public void setup() {
  }

  @Test
  public void products() throws Exception {
    when(productService.getAll()).thenReturn(Collections.emptyList());
    mockMvc.perform(get("/products"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(model().attribute("products", Collections.emptyList()))
        .andExpect(view().name("products"));
  }

  @Test
  public void deleteAll() throws Exception {
    when(userService.isAdmin(isNull())).thenReturn(true);

    mockMvc.perform(post("/deleteAll"))
        .andDo(print())
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/storage/add"))
        .andExpect(view().name("redirect:/storage/add"));

    verify(userService, times(1)).isAdmin(isNull());
    verifyNoMoreInteractions(userService);

    verify(productService, times(1)).deleteAll();
    verifyNoMoreInteractions(productService);
  }

  @Test
  public void deleteAllNotAuthenticated() throws Exception {
    when(userService.isAdmin(isNull())).thenReturn(false);

    mockMvc.perform(post("/deleteAll")
    )
        .andDo(print())
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.status", is("UNAUTHORIZED")))
        .andExpect(jsonPath("$.message", is("Request denied, admin role is required.")))
        .andExpect(jsonPath("$.path", is("/deleteAll")))
        .andExpect(jsonPath("$.timeStamp", containsString("")));

    verify(userService, times(1)).isAdmin(isNull());
    verifyNoMoreInteractions(userService);
  }

  }
