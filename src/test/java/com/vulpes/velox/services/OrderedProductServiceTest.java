package com.vulpes.velox.services;

import com.vulpes.velox.VeloxApplication;
import com.vulpes.velox.models.Order;
import com.vulpes.velox.models.OrderedProduct;
import com.vulpes.velox.services.orderedproductservice.OrderedProductService;
import com.vulpes.velox.services.orderservice.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = VeloxApplication.class, webEnvironment = RANDOM_PORT)
public class OrderedProductServiceTest {

  @Autowired
  private OrderedProductService orderedProductService;

  @Autowired
  private OrderService orderService;

  private OrderedProduct orderedProduct;
  private int countAllStart;
  private Order order;

  @Before
  public void setup() {
    orderedProduct = new OrderedProduct();
    orderedProduct.setProductName("ProductName");
    order = new Order();
    orderService.save(order);
    orderedProduct.setOrder(order);
    countAllStart = orderedProductService.getAll().size();
  }

  @Test
  public void save() {
    assertThat(orderedProductService.getAll().size(), is(countAllStart));
    orderedProductService.save(orderedProduct);
    assertThat(orderedProductService.getAll().size(), is(countAllStart + 1));
  }

  @Test
  public void getAllByOrder() {
    orderedProductService.save(orderedProduct);
    List<OrderedProduct> allByOrder = orderedProductService.getAllByOrder(order);
    assertThat(allByOrder.size(), is(1));
    assertThat(allByOrder.get(0).getProductName(), is("ProductName"));
  }

  @Test
  public void getAllByProductName() {
    orderedProductService.save(orderedProduct);
    List<OrderedProduct> allByProductName = orderedProductService.getAllByProductName("ProductName");
    assertThat(allByProductName.size(), is(1));
    assertThat(allByProductName.get(0).getProductName(), is("ProductName"));

    List<OrderedProduct> allByProductNameH2 = orderedProductService.getAllByProductName("NameTaken");
    assertThat(allByProductName.size(), is(1));
    assertThat(allByProductNameH2.get(0).getProductName(), is("NameTaken"));
  }

}
