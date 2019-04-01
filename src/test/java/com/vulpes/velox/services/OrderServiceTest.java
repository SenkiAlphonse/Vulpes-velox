package com.vulpes.velox.services;

import com.vulpes.velox.VeloxApplication;
import com.vulpes.velox.models.Order;
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

import static org.junit.Assert.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = VeloxApplication.class, webEnvironment = RANDOM_PORT)
public class OrderServiceTest {

  @Autowired
  private OrderService orderService;

  @Test
  public void getAll_Test() {
    List<Order> testList = orderService.getAll();
    int testListSize = testList.size();

    assertEquals(testListSize, 2);
  }

  @Test
  public void save_Test() {
    Order testOrder = new Order();
    testOrder.setName("newTestOrder");
    testOrder.setDate(new Date());

    orderService.save(testOrder);
    List<Order> testList = orderService.getAll();
    int testListSize = testList.size();

    assertEquals(testListSize, 3);
  }

  @Test
  public void save_Test_Fail() {
    Order testOrder = new Order();
    testOrder.setName("testName1");
    testOrder.setDate(new Date());

    orderService.save(testOrder);
    List<Order> testList = orderService.getAll();
    int testListSize = testList.size();

    assertEquals(testListSize, 2);
  }

  @Test
  public void getByName_Test() {
    Order testOrder = orderService.getByName("testName1");
    assertNotNull(testOrder);
  }

  @Test
  public void getByName_Test_Fail() {
    Order testOrder = orderService.getByName("dummyOrder");
    assertNull(testOrder);
  }

  @Test
  public void existByName_Test() {
    boolean testExistence = orderService.existsByName("testName1");
    assertTrue(testExistence);
  }

  @Test
  public void existByName_Test_False() {
    boolean testExistence = orderService.existsByName("dummyOrder");
    assertFalse(testExistence);
  }
}
