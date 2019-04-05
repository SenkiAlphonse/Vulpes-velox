package com.vulpes.velox.controllers;

import com.vulpes.velox.models.Order;
import com.vulpes.velox.models.OrderedProduct;
import com.vulpes.velox.services.orderedproductservice.OrderedProductService;
import com.vulpes.velox.services.orderservice.OrderService;
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

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = OrderedProductController.class, secure = false)
public class OrderedProductControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private OrderedProductService orderedProductService;

  @MockBean
  private OrderService orderService;

  private Order order;

  @Before
  public void setup() {
    order = new Order();
  }

  @Test
  public void addOrderedProducts() throws Exception {
    when(orderedProductService.getAllByOrder(order)).thenReturn(Collections.emptyList());
    when(orderService.getByName("Name")).thenReturn(order);

    mockMvc.perform(get("/order/Name"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(model().attribute("orderedProducts", Collections.emptyList()))
        .andExpect(model().attribute("orderName", "Name"))
        .andExpect(view().name("addOrderedProducts"));
  }

  @Test
  public void orderedProductNewOk() throws Exception{
    when(orderService.getByName("Name")).thenReturn(order);

    mockMvc.perform(post("/ordered-product/Name/add")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("id", "1")
        .param("productName", "Product")
        .param("quantity", "5")
    )
        .andDo(print())
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/order/Name"))
        .andExpect(view().name("redirect:/order/{orderName}"));

    verify(orderService, times(1))
        .getByName("Name");
    verifyNoMoreInteractions(orderService);

    ArgumentCaptor<OrderedProduct> orderedProductArgument = ArgumentCaptor.forClass(OrderedProduct.class);
    verify(orderedProductService, times(1))
        .save(orderedProductArgument.capture());
    verifyNoMoreInteractions(orderedProductService);

    OrderedProduct orderedProductArgumentValue = orderedProductArgument.getValue();
    assertThat(orderedProductArgumentValue.getId(), is((long) 1));
    assertThat(orderedProductArgumentValue.getProductName(), is("Product"));
    assertThat(orderedProductArgumentValue.getQuantity(), is((long) 5));
    assertThat(orderedProductArgumentValue.getOrder(), is(order));
  }



}
