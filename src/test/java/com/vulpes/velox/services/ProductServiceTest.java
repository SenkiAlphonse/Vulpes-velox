package com.vulpes.velox.services;

import com.vulpes.velox.VeloxApplication;
import com.vulpes.velox.models.Item;
import com.vulpes.velox.models.Shipment;
import com.vulpes.velox.models.products.BulkProduct;
import com.vulpes.velox.models.products.IdentifiedProduct;
import com.vulpes.velox.models.products.Product;
import com.vulpes.velox.services.productservice.ProductService;
import com.vulpes.velox.services.shipmentservice.ShipmentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = VeloxApplication.class, webEnvironment = RANDOM_PORT)
@SqlGroup({
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:schema.sql"),
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data.sql")})
public class ProductServiceTest {

  @Autowired
  private ProductService productService;

  @Autowired
  private ShipmentService shipmentService;

  private int countAllStart;
  private BulkProduct bulkProduct;
  private IdentifiedProduct identifiedProduct;
  private List<Product> products;

  @Before
  public void setup() {
    countAllStart = productService.getAll().size();
    bulkProduct = (BulkProduct) productService.getByName("NameTaken");
    identifiedProduct = (IdentifiedProduct) productService.getByName("NameTaken3");
    products = new ArrayList<>();
    products.add(bulkProduct);
    products.add(productService.getByName("NameTaken2"));
    products.add(productService.getByName("NameTaken3"));
    products.add(productService.getByName("NameTaken4"));
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
    assertThat(bulkProduct.getQuantity(), is((long) 3));
  }

  @Test
  public void getDtosFromEntities() {
    assertThat(productService.getDtosFromEntities(Collections.emptyList()), is(Collections.emptyList()));
    assertThat(productService.getDtosFromEntities(null), is(Collections.emptyList()));
    for (int i = 0; i < products.size(); i++) {
      assertThat(productService.getDtosFromEntities(products).get(i).name, is(products.get(i).getName()));
      assertThat(productService.getDtosFromEntities(products).get(i).quantity, is(products.get(i).getQuantity()));
    }
  }

  @Test
  public void updateBulkProductWithShipment() {
    productService.updateBulkProductWithShipment(
        "NameTaken", new Shipment((long) 10, (long) 10));
    BulkProduct bulkProductUpdated = (BulkProduct) productService.getByName("NameTaken");
    assertThat(bulkProductUpdated.getQuantity(), is((long) 13));
    assertThat(bulkProductUpdated.getValue(), is(BigInteger.valueOf(101)));
    assertThat(bulkProductUpdated.getPrice(), is((long) 101/13));
  }

  @Test
  public void updateIdentifiedProductWithItem() {
    Item item = new Item();
    item.setPrice((long) 100);

    productService.updateIdentifiedProductWithItem(identifiedProduct, item);

    assertThat(identifiedProduct.getQuantity(), is((long) 5));
    assertThat(identifiedProduct.getValue(), is(BigInteger.valueOf(106)));
    assertThat(identifiedProduct.getPrice(), is((long) 106/5));
  }

//  @Test
//  public void getAll() {
//    List<Product> productsAll = productService.getAll();
//    List<Product> products2 = Arrays.asList(new BulkProduct(), new IdentifiedProduct());
//    assertEquals(productsAll, products);
//    assertNotSame(productsAll, products);
//    assertNotEquals(productsAll, products2);
//  }

}
