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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
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
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("id", "1")
        .param("productNumber", "2")
        .param("identifiedProductToSet", "IdentifiedProductName")
    )
        .andDo(print())
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/storage/add"))
        .andExpect(view().name("redirect:/storage/add"));

    verify(userService, times(1)).isAuthorized(any());
    verifyNoMoreInteractions(userService);

    ArgumentCaptor<Item> itemArgument = ArgumentCaptor.forClass(Item.class);
    verify(itemService, times(1)).save(itemArgument.capture());
    verify(itemService, times(1)).getAllByIdentifiedProduct(identifiedProduct);
    verifyNoMoreInteractions(itemService);

    Item itemArgumentValue = itemArgument.getValue();
    assertThat(itemArgumentValue.getId(), is((long) 1));
    assertThat(itemArgumentValue.getProductNumber(), is((long) 2));
    assertThat(itemArgumentValue.getIdentifiedProduct(), is(identifiedProduct));

    verify(productService, times(1)).getByName("IdentifiedProductName");
    verify(productService, times(1)).update(identifiedProduct);
    verifyNoMoreInteractions(productService);
  }


}