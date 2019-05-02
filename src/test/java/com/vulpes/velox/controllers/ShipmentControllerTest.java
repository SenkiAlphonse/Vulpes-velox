package com.vulpes.velox.controllers;

import com.vulpes.velox.models.Shipment;
import com.vulpes.velox.services.bulkproductservice.BulkProductService;
import com.vulpes.velox.services.productservice.ProductService;
import com.vulpes.velox.services.shipmentservice.ShipmentService;
import com.vulpes.velox.services.userservice.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = ShipmentController.class, secure = false)
public class ShipmentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ShipmentService shipmentService;
  @MockBean
  private BulkProductService bulkProductService;
  @MockBean
  private ProductService productService;
  @MockBean
  private UserService userService;

  private Map<String, Boolean> errorFlashAttributes;

  @Before
  public void setup() {
    errorFlashAttributes = new HashMap<>();
    errorFlashAttributes.put("bulkProductError", true);
  }

  @Test
  public void shipments() throws Exception {
    when(shipmentService.getAll()).thenReturn(Collections.emptyList());
    mockMvc.perform(get("/shipments"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(model().attribute("shipments", Collections.emptyList()))
        .andExpect(view().name("shipments"));
  }

  @Test
  public void shipmentNewOk() throws Exception {
    when(userService.isUser(isNull())).thenReturn(true);
    when(shipmentService.getErrorFlashAttributes(
        notNull(),
        notNull(),
        notNull(),
        notNull(),
        notNull())).thenReturn(Collections.emptyMap());

    mockMvc.perform(post("/shipment/new")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("id", "1")
        .param("quantity", "5")
        .param("price", "100")
        .param("bulkProductToSet", "BulkProductName")
        .param("arrivalToSet", "Arrival")
        .param("bestBeforeToSet", "BestBefore")
    )
        .andDo(print())
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/storage/add#shipment"))
        .andExpect(view().name("redirect:/storage/add#shipment"));

    verify(userService, times(1)).isUser(isNull());
    verifyNoMoreInteractions(userService);

    ArgumentCaptor<Shipment> shipmentArgument = ArgumentCaptor.forClass(Shipment.class);
    ArgumentCaptor<String> stringArgument = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String> stringArgument2 = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String> stringArgument3 = ArgumentCaptor.forClass(String.class);
    verify(shipmentService, times(1))
        .saveNewShipment(
            stringArgument.capture(),
            stringArgument2.capture(),
            stringArgument3.capture(),
            shipmentArgument.capture());
    verify(shipmentService, times(1))
        .getNewShipmentFlashAttributes(any(Shipment.class), any(RedirectAttributes.class));
    verify(shipmentService, times(1))
        .getErrorFlashAttributes(
            anyString(),
            anyString(),
            anyString(),
            any(Shipment.class),
            any(RedirectAttributes.class));
    verifyNoMoreInteractions(shipmentService);

    verify(productService, times(1))
        .updateBulkProductWithShipment(anyString(), any(Shipment.class));
    verifyNoMoreInteractions(bulkProductService);

    Shipment shipmentArgumentValue = shipmentArgument.getValue();
    assertThat(shipmentArgumentValue.getId(), is((long) 1));
    assertThat(shipmentArgumentValue.getQuantity(), is((long) 5));
    assertThat(shipmentArgumentValue.getPrice(), is((long) 100));
    assertThat(stringArgument.getValue(), is("BulkProductName"));
    assertThat(stringArgument2.getValue(), is("Arrival"));
    assertThat(stringArgument3.getValue(), is("BestBefore"));
  }

  @Test
  public void shipmentNewWithoutPrice() throws Exception {
    when(userService.isUser(isNull())).thenReturn(true);
    when((Object) shipmentService.getErrorFlashAttributes(
        notNull(),
        notNull(),
        notNull(),
        notNull(),
        notNull())).thenReturn(errorFlashAttributes);

    mockMvc.perform(post("/shipment/new")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("id", "1")
        .param("quantity", "5")
        .param("bulkProductToSet", "BulkProductName")
        .param("arrivalToSet", "Arrival")
        .param("bestBeforeToSet", "BestBefore")
    )
        .andDo(print())
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/storage/add#shipment"))
        .andExpect(view().name("redirect:/storage/add#shipment"));

    verify(userService, times(1)).isUser(isNull());
    verifyNoMoreInteractions(userService);
    verify(shipmentService, times(1))
        .getErrorFlashAttributes(
            anyString(),
            anyString(),
            anyString(),
            any(Shipment.class),
            any(RedirectAttributes.class));
    verifyNoMoreInteractions(shipmentService);
  }

  @Test
  public void shipmentNewNotAuthenticated() throws Exception {
    when(userService.isUser(isNull())).thenReturn(false);

    mockMvc.perform(post("/shipment/new")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("id", "1")
        .param("quantity", "5")
        .param("price", "100")
        .param("bulkProductToSet", "BulkProductName")
        .param("arrivalToSet", "Arrival")
        .param("bestBeforeToSet", "BestBefore")
    )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("unauthorized"));

    verify(userService, times(1)).isUser(isNull());
    verify(userService, times(1)).getUserEmail(isNull());
    verifyNoMoreInteractions(userService);
    verifyZeroInteractions(shipmentService);
  }


}
