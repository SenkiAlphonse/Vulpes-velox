package com.vulpes.velox.services;

import com.vulpes.velox.VeloxApplication;
import com.vulpes.velox.models.Item;
import com.vulpes.velox.models.products.IdentifiedProduct;
import com.vulpes.velox.services.identifiedproductservice.IdentifiedProductService;
import com.vulpes.velox.services.itemservice.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = VeloxApplication.class, webEnvironment = RANDOM_PORT)
public class ItemServiceTest {

  @Autowired
  private ItemService itemService;

  @Autowired
  private IdentifiedProductService identifiedProductService;

  @Test
  @Transactional
  public void save_Test() {
    Item testItem = new Item();
    testItem.setId(2L);
    testItem.setProductNumber(111111L);
    itemService.save(testItem);

    List<Item> testList = itemService.getAll();
    int testListSize = testList.size();
    assertEquals(testListSize, 2);
  }

  @Test
  public void getAll_Test() {
    List<Item> helpList = itemService.getAll();
    int helpListSize = helpList.size();

    assertEquals(helpListSize, 1);
  }

  @Test
  public void getAllByIdentifiedProduct_Test() {
    List<IdentifiedProduct> helpList = identifiedProductService.getAll();
    IdentifiedProduct testIdentifiedProduct = helpList.get(0);

    List<Item> testList = itemService.getAllByIdentifiedProduct(testIdentifiedProduct);
    int testListSize = testList.size();

    assertEquals(testListSize, 1);
  }
}
