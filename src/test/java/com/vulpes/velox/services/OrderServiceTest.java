package com.vulpes.velox.services;

import com.vulpes.velox.VeloxApplication;
import com.vulpes.velox.models.Item;
import com.vulpes.velox.models.Order;
import com.vulpes.velox.models.products.IdentifiedProduct;
import com.vulpes.velox.services.identifiedproductservice.IdentifiedProductService;
import com.vulpes.velox.services.itemservice.ItemService;
import com.vulpes.velox.services.methodservice.MethodService;
import com.vulpes.velox.services.orderservice.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = VeloxApplication.class, webEnvironment = RANDOM_PORT)
@SqlGroup({
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:schema.sql"),
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data.sql")})
public class OrderServiceTest {

  @Autowired
  private OrderService orderService;

  private Map<String, Boolean> errorFlashAttributes;
  private Order order;

  @Before
  public void setup() {
    errorFlashAttributes = new HashMap<>();
    errorFlashAttributes.put("itemError", true);
    order = new Order();
  }

  @Test
  public void getAll() {
    assertFalse(orderService.getAll().isEmpty());
    assertThat(orderService.getAll().size(), is(2));
    assertThat(orderService.getAll().get(0).getName(), is("NameTaken"));
  }

  @Test
  public void save() {
    assertThat(orderService.getAll().size(), is(2));
    order.setName("NameTaken");
    orderService.save(order);
    assertThat(orderService.getAll().size(), is(2));
    order.setName("NameNew");
    orderService.save(order);
    assertThat(orderService.getAll().size(), is(3));
    assertThat(orderService.getAll().get(2).getName(), is("NameNew"));
  }

  @Test
  public void getByName() {
    Order orderByName = orderService.getByName("NameTaken");
    assertNotNull(orderByName);
    assertThat(orderByName.getName(), is("NameTaken"));
  }



}
