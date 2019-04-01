package com.vulpes.velox.services;

import com.vulpes.velox.VeloxApplication;
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

  private int countAllStart;

  @Before
  public void setup() {
    countAllStart = orderedProductService.getAll().size();
  }

  @Test
  public void save() {
    assertThat(orderedProductService.getAll().size(), is(countAllStart));
    orderedProductService.save(new OrderedProduct());
    assertThat(orderedProductService.getAll().size(), is(countAllStart + 1));
  }

  @Test
  public void getAllByOrder() {
    List<OrderedProduct> allByOrder = orderedProductService.getAllByOrder(orderService.getByName("NameTaken"));
    assertThat(allByOrder.size(), is(2));
    assertThat(allByOrder.get(0).getProductName(), is("NameTaken"));
    assertThat(allByOrder.get(1).getProductName(), is("NameTaken2"));
  }

  @Test
  public void getAllByProductName() {
    List<OrderedProduct> allByProductName = orderedProductService.getAllByProductName("NameTaken");
    assertThat(allByProductName.size(), is(1));
    assertThat(allByProductName.get(0).getProductName(), is("NameTaken"));

    List<OrderedProduct> allByProductNameH2 = orderedProductService.getAllByProductName("NameTaken2");
    assertThat(allByProductName.size(), is(1));
    assertThat(allByProductNameH2.get(0).getProductName(), is("NameTaken2"));
  }

}
