package com.vulpes.velox.services;

import com.vulpes.velox.models.products.IdentifiedProduct;
import com.vulpes.velox.services.identifiedproductservice.IdentifiedProductService;
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
public class IdentifiedProductServiceTest {

  @Autowired
  private IdentifiedProductService identifiedProductService;

  @Test
  public void getAll() {
    List<IdentifiedProduct> testList = identifiedProductService.getAll();
    int testListSize = testList.size();
    assertEquals(testListSize, 1);
  }
}
