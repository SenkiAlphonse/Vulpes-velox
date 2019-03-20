package com.vulpes.velox.controllers;

import com.vulpes.velox.models.IdentifiedProduct;
import com.vulpes.velox.models.Item;
import com.vulpes.velox.services.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.apache.commons.lang3.StringUtils.contains;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = StorageController.class, secure = false)
public class StorageControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  UserService userService;

  @MockBean
  ProductService productService;

  @MockBean
  IdentifiedProductService identifiedProductService;

  @MockBean
  ItemService itemService;

  @MockBean
  BulkProductService bulkProductService;

  @MockBean
  ShipmentService shipmentService;

  private IdentifiedProduct identifiedProduct;
  private Item itemNew;

  @Before
  public void setup() {
    identifiedProduct = new IdentifiedProduct();
    itemNew = new Item();
  }

  @Test
  public void itemNew() throws Exception {
    when(userService.isAuthorized(any())).thenReturn(true);
    when(productService.getByName("IdentifiedProductName")).thenReturn(identifiedProduct);

    mockMvc.perform(post("/item/new")
        .param("identifiedProductToSet", "IdentifiedProductName")
    )
        .andDo(print())
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/storage/add"));
//        .andExpect(model().attributeExists("itemNew"));
//        .andExpect(model().attribute("itemNew", is(itemNew)));
  }


}
