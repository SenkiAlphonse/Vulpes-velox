package com.vulpes.velox.services;

import com.vulpes.velox.models.Item;
import com.vulpes.velox.services.itemservice.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:schema.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:schema.sql")
})
public class ItemServiceTest {

  @Autowired
  private ItemService itemService;

  @Test
  public void save_Test() {
    Item testItem = new Item();
    testItem.setId(2L);
    testItem.setProductNumber(111111L);
    itemService.save(testItem);

    List<Item> testList = itemService.getAll();
    int testListSize = testList.size();
    assertEquals(testListSize, 2);
  }

//  @Test
//  public void getAllByIdentifiedProduct_Test() {
//    List<Item> testList = itemService.getAllByIdentifiedProduct();
//  }
}
