package com.vulpes.velox.services;

import com.vulpes.velox.models.products.BulkProduct;
import com.vulpes.velox.models.products.Product;
import com.vulpes.velox.services.productservice.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:schema.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:schema.sql")
})
public class ProductServiceTest {

  @Autowired
  private ProductService productService;

  @Test
  @Transactional
  public void save_Test() {
    Product addNewProduct = new BulkProduct();
    addNewProduct.setName("updatedProduct22");
    productService.save(addNewProduct);

    List<Product> testList = productService.getAll();
    int testListSize = testList.size();
    assertEquals(testListSize, 3);
  }

  @Test
  public void save_Test_Fail() {
    Product addNewProduct = new BulkProduct();
    addNewProduct.setName("testProduct1");
    productService.save(addNewProduct);

    List<Product> testList = productService.getAll();
    int testListSize = testList.size();
    assertEquals(testListSize, 2);
  }

  @Test
  @Transactional
  public void update_Test() {
    Product productUpdate = new BulkProduct();
    productUpdate.setId(1L);
    productUpdate.setName("updatedProduct");
    productService.update(productUpdate);

    List<Product> testList = productService.getAll();
    int testListSize = testList.size();
    assertEquals(testListSize, 2);
  }

  @Test
  @Transactional
  public void deleteAll_Test() {
    productService.deleteAll();
    List<Product> testList = productService.getAll();
    int testListSize = testList.size();
    assertEquals(testListSize, 0);
  }

  @Test
  public void existByName_Test() {
    boolean productExist = productService.existsByName("testProduct1");
    assertTrue(productExist);
  }

  @Test
  public void existByName_Test_False() {
    boolean productExist = productService.existsByName("testProduct11");
    assertFalse(productExist);
  }

  @Test
  public void getByName_Test() {
    Product testProduct = productService.getByName("testProduct1");
    long testProductId = testProduct.getId();
    assertEquals(testProductId, 1L);
  }

  @Test
  public void getByName_Test_False() {
    Product testProduct = productService.getByName("testProduct11");
    assertNull(testProduct);
  }

  @Test
  public void getAll_Test() {
    List<Product> testList = productService.getAll();
    int testListSize = testList.size();
    assertEquals(testListSize, 2);
  }
}
