package com.vulpes.velox.services;

import com.vulpes.velox.models.products.BulkProduct;
import com.vulpes.velox.services.bulkproductservice.BulkProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:schema.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:schema.sql")
})
public class BulkProductServiceTest {

  @Autowired
  private BulkProductService bulkProductService;

  @Test
  public void getAll_Test() {
    List<BulkProduct> listOfBulkProducts = bulkProductService.getAll();
    int listOfBulkProductsSize = listOfBulkProducts.size();
    assertEquals(listOfBulkProductsSize, 1);
  }

  @Test
  public void existsByName_Test() {
    boolean isExist = bulkProductService.existsByName("testProduct1");
    assertTrue(isExist);
  }

  @Test
  public void existsByName_Test_False() {
    boolean isExist = bulkProductService.existsByName("testProduct2");
    assertFalse(isExist);
  }
}
