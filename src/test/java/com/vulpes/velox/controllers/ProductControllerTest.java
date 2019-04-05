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
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = ProductControllerTest.class, secure = false)
public class ProductControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ProductService productService;
  @MockBean
  private UserService userService;

  private Map<String, Boolean> errorFlashAttributes;

  @Before
  public void setup() {
    errorFlashAttributes = new HashMap<>();
    errorFlashAttributes.put("orderError", true);
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


}
