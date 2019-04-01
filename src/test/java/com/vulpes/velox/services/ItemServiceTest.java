package com.vulpes.velox.services;

import com.vulpes.velox.VeloxApplication;
import com.vulpes.velox.models.products.IdentifiedProduct;
import com.vulpes.velox.services.identifiedproductservice.IdentifiedProductService;
import com.vulpes.velox.services.itemservice.ItemService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = VeloxApplication.class, webEnvironment = RANDOM_PORT)
@SqlGroup({
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:schema.sql"),
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data.sql")})
public class ItemServiceTest {

  @Autowired
  private ItemService itemService;

  @Autowired
  private IdentifiedProductService identifiedProductService;

  private List<IdentifiedProduct> identifiedProducts;
  private IdentifiedProduct identifiedProduct;

  @Before
  public void setup() {
    identifiedProducts = identifiedProductService.getAll();
    identifiedProduct = identifiedProducts.get(0);
  }

  @Test
  public void getAllByIdentifiedProduct() {
    assertThat(itemService.getAllByIdentifiedProduct(identifiedProduct).size(), is(1));
    assertThat(itemService.getAllByIdentifiedProduct(identifiedProduct), instanceOf(List.class));
    assertThat(itemService.getAllByIdentifiedProduct(identifiedProduct).get(0).getProductNumber(), is((long) 11111111));
  }

//  @Test
//  void save() {
//
//  }
//
//  @Test
//  void getAll() {
//
//  }
//
//  @Test
//  void existsByProductNumber() {
//
//  }
//
//  @Test
//  void getErrorFlashAttributes() {
//
//  }
//
//  @Test
//  void getNewItemFlashAttributes() {
//
//  }
}
