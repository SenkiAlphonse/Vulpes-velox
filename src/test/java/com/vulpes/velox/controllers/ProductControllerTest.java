package com.vulpes.velox.controllers;

import com.vulpes.velox.config.WebSecurityConfig;
import com.vulpes.velox.models.products.IdentifiedProduct;
import com.vulpes.velox.services.itemservice.ItemService;
import com.vulpes.velox.services.productservice.ProductService;
import com.vulpes.velox.services.userservice.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static io.restassured.authentication.FormAuthConfig.springSecurity;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = ProductController.class, secure = false)
public class ProductControllerTest {

  private MediaType contentType = new MediaType(MediaType.APPLICATION_FORM_URLENCODED.getType(),
          MediaType.APPLICATION_FORM_URLENCODED.getSubtype(),
          Charset.forName("utf8"));

  private MockMvc mockMvc;

  @Autowired
  private WebSecurityConfig webSecurityConfig;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @MockBean
  private ProductService productService;
  @MockBean
  private UserService userService;
  @MockBean
  private ItemService itemService;

  private IdentifiedProduct identifiedProduct;

  @Before
  public void setup() {
//    mockMvc = webAppContextSetup(webApplicationContext)
//            .apply(springSecurity())
//            .build();
  }

  @Test
  public void deleteAll_Test() throws Exception {
    when(userService.isAdmin(any())).thenReturn(true);

  }
}