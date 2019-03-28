package com.vulpes.velox.services;

import com.vulpes.velox.models.Order;
import com.vulpes.velox.models.OrderedProduct;
import com.vulpes.velox.services.orderedproductservice.OrderedProductService;
import com.vulpes.velox.services.orderservice.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:schema.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:schema.sql")
})
public class OrderedProductServiceTest {

  @Autowired
  private OrderedProductService orderedProductService;

  @Autowired
  private OrderService orderService;

  @Test
  public void save_Test() {
    Order testOrder = new Order();
    testOrder.setId(3L);
    testOrder.setName("mockTestOrder");
    testOrder.setDate(new Date());
    orderService.save(testOrder);

    OrderedProduct testOrderedProduct = new OrderedProduct();
    testOrderedProduct.setId(3L);
    testOrderedProduct.setProductName("testOrderName");
    testOrderedProduct.setOrder(testOrder);
    testOrderedProduct.setQuantity(5L);
    orderedProductService.save(testOrderedProduct);

    List<OrderedProduct> testList = orderedProductService.getAll();
    int testListSize = testList.size();
    assertEquals(testListSize, 3);
  }

  @Test
  public void getAll_Test() {
    List<OrderedProduct> orderedList = orderedProductService.getAll();
    int orderedListSize = orderedList.size();
    assertEquals(orderedListSize, 2);
  }
  @Test
  public void getAllByOrder_Test() {
    Order orderedTest = orderService.getByName("testName1");

    List<OrderedProduct> orderedList = orderedProductService.getAllByOrder(orderedTest);
    int orderedListSize = orderedList.size();
    assertEquals(orderedListSize, 1);
  }

  @Test
  public void getAllByProductName() {
    List<OrderedProduct> orderedList = orderedProductService.getAllByProductName("testProduct1");
    int orderedListSize = orderedList.size();
    assertEquals(orderedListSize, 1);
  }


}
