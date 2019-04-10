package com.vulpes.velox.services;

import com.vulpes.velox.VeloxApplication;
import com.vulpes.velox.models.Shipment;
import com.vulpes.velox.models.products.BulkProduct;
import com.vulpes.velox.services.bulkproductservice.BulkProductService;
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

  @MockBean
  private MethodService methodService;
  @MockBean
  private RedirectAttributes redirectAttributes;

  private Map<String, Boolean> errorFlashAttributes;
  private int countAllStart;
  private BulkProduct bulkProduct;
  private Shipment shipment;

  @Before
  public void setup() {
    errorFlashAttributes = new HashMap<>();
    errorFlashAttributes.put("shipmentError", true);
    countAllStart = shipmentService.getAll().size();
    bulkProduct = bulkProductService.getAll().get(0);
    shipment = new Shipment();
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
    List<Shipment> allByBulkProduct = shipmentService.getAllByBulkProduct(bulkProduct);
    assertThat(allByBulkProduct.size(), is(1));
    assertThat(allByBulkProduct.get(0).getQuantity(), is((long) 5));
  }

  @Test
  public void isAllowedDateFormat() {
    assertFalse(shipmentService.isAllowedDateFormat("string"));
    assertTrue(shipmentService.isAllowedDateFormat("0123456789"));
  }

  @Test
  public void getErrorFlashAttributes() {
    String bulkProductName = "";
    String arrivalDate = "";
    String bestBeforeDate = "";
    String message = "Enter quantity.";

    when((Object) methodService.getErrorMessageFlashAttributes(
        notNull(),
        notNull(),
        notNull())).thenReturn(errorFlashAttributes);

    for (int i = 0; i < 8; i++) {
      if (i == 1) {
        shipment.setQuantity((long) 0);
        message = "Quantity not allowed.";
      } else if (i == 2) {
        shipment.setQuantity((long) 10);
        message = "Enter price.";
      } else if (i == 3) {
        shipment.setPrice((long) 0);
        message = "Price not allowed.";
      } else if (i == 4) {
        shipment.setPrice((long) 400);
        message = "Date entered not allowed.";
      } else if (i == 5) {
        arrivalDate = "2019-10-01";
        bestBeforeDate = "2019-10-20";
        bulkProductName = null;
        message = "Enter bulk product name.";
      } else if (i == 6) {
        bulkProductName = "";
        message = "Empty bulk product name.";
      } else if (i == 7) {
        bulkProductName = "NotFound";
        message = "Bulk product not found.";
      }

      assertFalse(shipmentService.getErrorFlashAttributes(
          bulkProductName, arrivalDate, bestBeforeDate, shipment, redirectAttributes).isEmpty());

      ArgumentCaptor<String> stringArgument = ArgumentCaptor.forClass(String.class);
      ArgumentCaptor<String> stringArgument2 = ArgumentCaptor.forClass(String.class);
      verify(methodService, atLeast(1))
          .getErrorMessageFlashAttributes(
              stringArgument.capture(),
              any(RedirectAttributes.class),
              stringArgument2.capture());
      verifyNoMoreInteractions(methodService);
      assertThat(stringArgument.getValue(), is(message));
      assertThat(stringArgument2.getValue(), is("shipmentError"));
      clearInvocations();
    }
  }

  @Test
  public void getNewShipmentFlashAttributes() {
    when((Object) redirectAttributes.addFlashAttribute(
        notNull(), notNull())).thenReturn(redirectAttributes);
    when((Object) redirectAttributes.getFlashAttributes()).thenReturn(errorFlashAttributes);

    shipment.setPrice((long) 150);
    shipment.setArrival(LocalDate.of(2019,8,10));
    shipment.setBestBefore(LocalDate.of(2019,10,10));
    shipment.setQuantity((long) 5);
    shipment.setBulkProduct(bulkProduct);

    assertFalse(shipmentService.getNewShipmentFlashAttributes(shipment, redirectAttributes).isEmpty());

    ArgumentCaptor<String> stringArgument = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<LocalDate> localDateArgument = ArgumentCaptor.forClass(LocalDate.class);
    verify(redirectAttributes, atLeast(1))
        .addFlashAttribute(
            stringArgument.capture(),
            any(Boolean.class));
    verify(redirectAttributes, atLeast(1))
        .addFlashAttribute(
            stringArgument.capture(),
            anyString());
    verify(redirectAttributes, atLeast(1))
        .addFlashAttribute(
            stringArgument.capture(),
            any(Long.class));
    verify(redirectAttributes, atLeast(1))
        .addFlashAttribute(
            stringArgument.capture(),
            any(LocalDate.class));
    verify(redirectAttributes, atLeast(1))
        .addFlashAttribute(
            anyString(),
            localDateArgument.capture());
    List<String> stringArgumentValue = stringArgument.getAllValues();
    LocalDate localDateArgumentValue = localDateArgument.getValue();
    assertThat(stringArgumentValue.get(0), is("savedShipment"));
    assertThat(stringArgumentValue.get(1), is("bulkProductName"));
    assertThat(stringArgumentValue.get(2), is("quantity"));
    assertThat(stringArgumentValue.get(3), is("arrival"));
    assertThat(localDateArgumentValue, is(LocalDate.of(2019,10,10)));
  }

  @Test
  public void saveNewShipment() {
    assertThat(shipmentService.getAll().size(), is(countAllStart));
    shipmentService.saveNewShipment(
        "NameTaken",
        "2019-10-05",
        "2019-10-20",
        shipment);
    assertThat(shipmentService.getAll().size(), is(countAllStart + 1));
    assertThat(shipment.getBulkProduct().getName(), is("NameTaken"));
    assertThat(shipment.getArrival(), is(LocalDate.of(2019,10,5)));
    assertThat(shipment.getBestBefore(), is(LocalDate.of(2019,10,20)));
  }

}
