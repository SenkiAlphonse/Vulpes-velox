package com.vulpes.velox.controllers;

import com.vulpes.velox.models.Item;
import com.vulpes.velox.models.products.IdentifiedProduct;
import com.vulpes.velox.services.itemservice.ItemService;
import com.vulpes.velox.services.productservice.ProductService;
import com.vulpes.velox.services.userservice.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = ProductController.class, secure = false)
public class ProductControllerTest {
  @Autowired
  MockMvc mockMvc;

  @MockBean
  private ProductService productService;
  @MockBean
  private UserService userService;
  @MockBean
  private ItemService itemService;

  private IdentifiedProduct identifiedProduct;

  @Before
  public void setup() {
    identifiedProduct = new IdentifiedProduct();
  }

  @Test
  public void deleteAll_Test() throws Exception {
    when(userService.isAdmin(any())).thenReturn(true);

  }
}