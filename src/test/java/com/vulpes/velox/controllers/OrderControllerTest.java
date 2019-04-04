package com.vulpes.velox.controllers;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.vulpes.velox.models.Order;
import com.vulpes.velox.models.products.IdentifiedProduct;
import com.vulpes.velox.services.identifiedproductservice.IdentifiedProductService;
import com.vulpes.velox.services.orderservice.OrderService;
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

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = OrderController.class, secure = false)
public class OrderControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private OrderService orderService;

  private Map<String, Boolean> errorFlashAttributes;

  public void setup() {
    errorFlashAttributes = new HashMap<>();
    errorFlashAttributes.put("orderError", true);

  }

  @Test
  public void order() throws Exception {
    mockMvc.perform(get("/order"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("order"));
  }

  @Test
  public void orderNewOk() throws Exception {
    when((Object) orderService.getErrorFlashAttributes(
        notNull(), notNull())).thenReturn(Collections.emptyMap());

    mockMvc.perform(post("/order/new")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("id", "1")
        .param("name", "Name")
    )
        .andDo(print())
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/order/Name"))
        .andExpect(view().name("redirect:/order/{orderName}"));

    ArgumentCaptor<Order> orderArgument = ArgumentCaptor.forClass(Order.class);
    verify(orderService, times(1))
        .save(orderArgument.capture());
    verify(orderService, times(1))
        .getErrorFlashAttributes(any(Order.class), any(RedirectAttributes.class));

    verifyNoMoreInteractions(orderService);

    Order orderArgumentValue = orderArgument.getValue();
    assertThat(orderArgumentValue.getId(), is((long) 1));
    assertThat(orderArgumentValue.getName(), is("Name"));
    assertThat(orderArgumentValue.getDate(), is(Date.class));
  }

  @Test
  public void orderNewNameTaken() throws Exception {
    when((Object) orderService.getErrorFlashAttributes(
        notNull(), notNull())).thenReturn(errorFlashAttributes);

    mockMvc.perform(post("/order/new")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("id", "1")
        .param("name", "Name")
    )
        .andDo(print())
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/order"))
        .andExpect(view().name("redirect:/order"));

    ArgumentCaptor<Order> orderArgument = ArgumentCaptor.forClass(Order.class);
    verify(orderService, times(1))
        .getErrorFlashAttributes(any(Order.class), any(RedirectAttributes.class));
    verifyNoMoreInteractions(orderService);

    Order orderArgumentValue = orderArgument.getValue();
    assertThat(orderArgumentValue.getId(), is((long) 1));
    assertThat(orderArgumentValue.getName(), is(nullValue()));
    assertThat(orderArgumentValue.getDate(), is(nullValue()));
  }



}


