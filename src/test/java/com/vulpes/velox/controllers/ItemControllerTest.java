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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.Assert.assertThat;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = ItemController.class, secure = false)
public class ItemControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ProductService productService;
  @MockBean
  private ItemService itemService;
  @MockBean
  private UserService userService;

  private IdentifiedProduct identifiedProduct;
  private Map<String, Boolean> errorFlashAttributes;
  private List<Item> items;

  @Before
  public void setup() {
    identifiedProduct = new IdentifiedProduct();
    errorFlashAttributes = new HashMap<>();
    errorFlashAttributes.put("itemError", true);
    items = Arrays.asList(new Item(), new Item());
  }


  @Test
  public void itemNewIsFound() throws Exception {
    when(userService.isUser(isNull())).thenReturn(true);
    when(itemService.getErrorFlashAttributes(
        notNull(), notNull(), notNull())).thenReturn(Collections.emptyMap());
    when(itemService.getNewItemFlashAttributes(
        notNull(), notNull())).thenReturn(Collections.emptyMap());
    when(productService.getByName("IdentifiedProductName")).thenReturn(identifiedProduct);
    when(itemService.getAllByIdentifiedProduct(identifiedProduct)).thenReturn(items);


    mockMvc.perform(post("/item/new")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("id", "1")
        .param("productNumber", "2")
        .param("identifiedProductToSet", "IdentifiedProductName")
    )
        .andDo(print())
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/storage/add#item"))
        .andExpect(view().name("redirect:/storage/add#item"));

    verify(userService, times(1)).isUser(isNull());
    verifyNoMoreInteractions(userService);

    ArgumentCaptor<Item> itemArgument = ArgumentCaptor.forClass(Item.class);
    verify(itemService, times(1)).save(itemArgument.capture());
    verify(itemService, times(1)).getAllByIdentifiedProduct(identifiedProduct);
    verify(itemService, times(1)).getErrorFlashAttributes(
        anyString(), any(Item.class), any(RedirectAttributes.class));
    verify(itemService, times(1)).getNewItemFlashAttributes(
        any(Item.class), any(RedirectAttributes.class));
    verifyNoMoreInteractions(itemService);

    assertThat(identifiedProduct.getQuantity(), is((long) 2));

    Item itemArgumentValue = itemArgument.getValue();
    assertThat(itemArgumentValue.getId(), is((long) 1));
    assertThat(itemArgumentValue.getProductNumber(), is((long) 2));
    assertThat(itemArgumentValue.getIdentifiedProduct(), is(identifiedProduct));

    verify(productService, times(1)).getByName("IdentifiedProductName");
    verify(productService, times(1)).update(identifiedProduct);
    verifyNoMoreInteractions(productService);

  }

  @Test
  public void itemNewWithoutIdentifiedProduct() throws Exception {
    when(userService.isUser(any())).thenReturn(true);
    when((Object) itemService.getErrorFlashAttributes(
        isNull(), notNull(), notNull())).thenReturn(errorFlashAttributes);

    mockMvc.perform(post("/item/new")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("id", "1")
        .param("productNumber", "2")
    )
        .andDo(print())
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/storage/add#item"))
        .andExpect(view().name("redirect:/storage/add#item"));

    verify(itemService, times(1)).getErrorFlashAttributes(
        isNull(), any(Item.class), any(RedirectAttributes.class));
    verifyNoMoreInteractions(itemService);
  }

  @Test
  public void itemNewWithoutAuthentication() throws Exception {
    when(userService.isUser(any())).thenReturn(false);
    when((Object) itemService.getErrorFlashAttributes(
        isNull(), notNull(), notNull())).thenReturn(errorFlashAttributes);

    mockMvc.perform(post("/item/new")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("id", "1")
        .param("productNumber", "2")
        .param("identifiedProductToSet", "IdentifiedProductName")
    )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("unauthorized"));

    verify(userService, times(1)).isUser(isNull());
    verify(userService, times(1)).getUserEmail(isNull());
    verifyNoMoreInteractions(userService);
    verifyNoMoreInteractions(itemService);
    verifyNoMoreInteractions(productService);
  }

  @Test
  public void identifiedProducts() throws Exception {
    when(itemService.getAll()).thenReturn(items);

    mockMvc.perform(get("/items"))
        .andExpect(status().isOk())
        .andExpect(model().attribute("items", items))
        .andExpect(content().contentType(new MediaType("text", "html", StandardCharsets.UTF_8)))
        .andExpect(view().name("items"));
  }


}
