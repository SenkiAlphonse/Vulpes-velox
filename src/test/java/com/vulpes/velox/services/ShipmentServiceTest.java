package com.vulpes.velox.services;

import com.vulpes.velox.VeloxApplication;
import com.vulpes.velox.models.Item;
import com.vulpes.velox.models.Shipment;
import com.vulpes.velox.models.products.IdentifiedProduct;
import com.vulpes.velox.services.bulkproductservice.BulkProductService;
import com.vulpes.velox.services.identifiedproductservice.IdentifiedProductService;
import com.vulpes.velox.services.itemservice.ItemService;
import com.vulpes.velox.services.methodservice.MethodService;
import com.vulpes.velox.services.shipmentservice.ShipmentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = VeloxApplication.class, webEnvironment = RANDOM_PORT)
@SqlGroup({
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:schema.sql"),
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data.sql")})
public class ShipmentServiceTest {

  @Autowired
  private ShipmentService shipmentService;

  @Autowired
  private BulkProductService bulkProductService;

  private Map<String, Boolean> errorFlashAttributes;
  private int countAllStart;

  @Before
  public void setup() {
    errorFlashAttributes = new HashMap<>();
    errorFlashAttributes.put("itemError", true);
    countAllStart = shipmentService.getAll().size();
  }

  @Test
  public void save() {
    assertThat(shipmentService.getAll().size(), is(countAllStart));
    shipmentService.save(new Shipment());
    assertThat(shipmentService.getAll().size(), is(countAllStart + 1));
  }

  @Test
  public void getLocalDateFromDateString() {
    assertThat(shipmentService.getLocalDateFromDateString("2019-10-01"),
        is(LocalDate.of(2019,10,1)));
  assertThat(shipmentService.getLocalDateFromDateString("2019-10-10"),
        is(LocalDate.of(2019,10,10)));
  }

  @Test
  public void getAll() {
    assertFalse(shipmentService.getAll().isEmpty());
    assertThat(shipmentService.getAll().size(), is(1));
    assertThat(shipmentService.getAll().get(0).getQuantity(), is((long) 5));
  }

  @Test
  public void getAllByBulkProduct() {
    List<Shipment> allByBulkProduct = shipmentService.getAllByBulkProduct(bulkProductService.getAll().get(0));
    assertThat(allByBulkProduct.size(), is(1));
    assertThat(allByBulkProduct.get(0).getQuantity(), is((long) 5));
  }

  @Test
  public void isAllowedDateFormat() {
    assertFalse(shipmentService.isAllowedDateFormat("string"));
    assertTrue(shipmentService.isAllowedDateFormat("0123456789"));
  }



}
