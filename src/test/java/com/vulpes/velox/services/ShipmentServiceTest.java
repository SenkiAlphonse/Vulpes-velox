package com.vulpes.velox.services;

import com.vulpes.velox.models.Shipment;
import com.vulpes.velox.models.products.BulkProduct;
import com.vulpes.velox.services.bulkproductservice.BulkProductService;
import com.vulpes.velox.services.shipmentservice.ShipmentService;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:schema.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:schema.sql")
})
public class ShipmentServiceTest {

  @Autowired
  private ShipmentService shipmentService;

  @Autowired
  private BulkProductService bulkProductService;

  @Test
  @Transactional
  public void save_Test() {
    Shipment testShipment = new Shipment();
    testShipment.setId(3L);
    shipmentService.save(testShipment);

    List<Shipment> testList = shipmentService.getAll();
    int testListSize = testList.size();
    assertEquals(testListSize, 2);
  }

  @Test
  public void getAllByBulkProduct() {
    BulkProduct testProduct = bulkProductService.getAll().get(0);
    List<Shipment> testList = shipmentService.getAllByBulkProduct(testProduct);
    int testListSize = testList.size();

    assertEquals(testListSize, 1);
  }
}
