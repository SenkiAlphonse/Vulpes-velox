package com.vulpes.velox.services;

import com.vulpes.velox.VeloxApplication;
import com.vulpes.velox.models.products.BulkProduct;
import com.vulpes.velox.models.products.Product;
import com.vulpes.velox.services.productservice.ProductService;
import com.vulpes.velox.services.shipmentservice.ShipmentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = VeloxApplication.class, webEnvironment = RANDOM_PORT)
public class ProductServiceTest {

  @Autowired
  private ProductService productService;

  @Autowired
  private ShipmentService shipmentService;

  private int countAllStart;
  private BulkProduct bulkProduct;
  private List<Product> products;

  @Before
  public void setup() {
    countAllStart = productService.getAll().size();
    bulkProduct = (BulkProduct) productService.getByName("NameTaken");
    products = new ArrayList<>();
    products.add(bulkProduct);
    products.add(productService.getByName("NameTaken3"));
  }

  @Test
  public void save() {
    assertThat(productService.getAll().size(), is(countAllStart));
    productService.save(bulkProduct);
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
    assertTrue(productService.existsByName("NameTaken"));
    assertFalse(productService.existsByName("NameNew"));
  }

  @Test
  public void getByName() {
    assertNotNull(bulkProduct);
    assertThat(bulkProduct.getName(), is("NameTaken"));
//    assertThat(bulkProduct.getQuantity(), is((long) 3));
  }

  @Test
  public void getDtosFromEntities() {
    assertThat(productService.getDtosFromEntities(Collections.emptyList()), is(Collections.emptyList()));
    assertThat(productService.getDtosFromEntities(null), is(Collections.emptyList()));
    assertThat(productService.getDtosFromEntities(products).get(0).name, is(products.get(0).getName()));
    assertThat(productService.getDtosFromEntities(products).get(1).name, is(products.get(1).getName()));
    assertThat(productService.getDtosFromEntities(products).get(0).quantity, is(products.get(0).getQuantity()));
    assertThat(productService.getDtosFromEntities(products).get(1).quantity, is(products.get(1).getQuantity()));
  }

//  @Test
//  public void getDtoFromEntity() {
//
//  }

//  @Test
//  public void getAll() {
//
//  }

//  @Test
//  public void updateBulkProductWithShipment() {
//    productService.updateBulkProductWithShipment("NameTaken", new Shipment((long) 10));
//    assertThat(bulkProduct.getQuantity(), is((long) 13));
//  }

}
