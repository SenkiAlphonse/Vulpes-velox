package com.vulpes.velox.services;


import com.vulpes.velox.VeloxApplication;
import com.vulpes.velox.models.products.BulkProduct;
import com.vulpes.velox.services.productservice.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = VeloxApplication.class, webEnvironment = RANDOM_PORT)
public class ProductServiceTest {

  @Autowired
  private ProductService productService;

  private int countAllStart;
  private BulkProduct bulkProduct;
  @Before
  public void setup() {
    countAllStart = productService.getAll().size();
    bulkProduct = (BulkProduct) productService.getByName("NameTaken");
  }

  @Test
  public void save() {
    assertThat(productService.getAll().size(), is(countAllStart));
    productService.save(productService.getByName("NameTaken"));
    assertThat(productService.getAll().size(), is(countAllStart));
    productService.save(new BulkProduct());
    assertThat(productService.getAll().size(), is(countAllStart + 1));
  }

  @Test
  public void update() {
    assertThat(productService.getAll().size(), is(countAllStart));
    productService.update(new BulkProduct());
    assertThat(productService.getAll().size(), is(countAllStart));
    bulkProduct.setQuantity((long) 10);
    productService.update(bulkProduct);
    assertThat(productService.getAll().size(), is(countAllStart));
    assertThat(bulkProduct.getQuantity(), is((long) 10));
  }

  @Test
  public void deleteAll() {
    assertThat(productService.getAll().size(), is(countAllStart));
    productService.deleteAll();
    assertThat(productService.getAll().size(), is(0));
  }

  @Test
  public void existsByName() {

  }

  @Test
  public void getByName() {

  }

  @Test
  public void getDtosFromEntities() {

  }

  @Test
  public void getDtoFromEntity() {

  }

  @Test
  public void getAll() {

  }

  @Test
  public void updateBulkProductWithShipment() {

  }

}
