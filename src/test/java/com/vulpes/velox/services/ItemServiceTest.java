package com.vulpes.velox.services;

import com.vulpes.velox.VeloxApplication;
import com.vulpes.velox.models.Item;
import com.vulpes.velox.models.products.IdentifiedProduct;
import com.vulpes.velox.repositories.ItemRepository;
import com.vulpes.velox.services.identifiedproductservice.IdentifiedProductService;
import com.vulpes.velox.services.itemservice.ItemService;
import com.vulpes.velox.services.itemservice.ItemServiceImpl;
import com.vulpes.velox.services.methodservice.MethodService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = VeloxApplication.class, webEnvironment = RANDOM_PORT)
@SqlGroup({
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:schema.sql"),
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data.sql")})
public class ItemServiceTest {

  @Autowired
  private ItemService itemService;

  @Autowired
  private IdentifiedProductService identifiedProductService;

  @MockBean
  private MethodService methodService;

  @MockBean
  private RedirectAttributes redirectAttributes;

  private List<IdentifiedProduct> identifiedProducts;
  private IdentifiedProduct identifiedProduct;
  private Map<String, Boolean> errorFlashAttributes;
  private Item item;

  @Before
  public void setup() {
    identifiedProducts = identifiedProductService.getAll();
    identifiedProduct = identifiedProducts.get(0);
    errorFlashAttributes = new HashMap<>();
    errorFlashAttributes.put("itemError", true);
    item = new Item();
  }

  @Test
  public void getAllByIdentifiedProduct() {
    assertThat(itemService.getAllByIdentifiedProduct(identifiedProduct).size(), is(1));
    assertThat(itemService.getAllByIdentifiedProduct(identifiedProduct), instanceOf(List.class));
    assertThat(itemService.getAllByIdentifiedProduct(identifiedProduct).get(0).getProductNumber(), is((long) 11111111));
  }

  @Test
  public void getErrorFlashAttributes() {

    String identifiedProductName = identifiedProduct.getName();
    String message = "Enter product number.";

    when((Object) methodService.getErrorMessageFlashAttributes(
        notNull(),
        notNull(),
        notNull())).thenReturn(errorFlashAttributes);

    for (int i = 0; i < 7; i++) {
      if (i == 1) {
        item.setProductNumber((long) 2135);
        message = "Product number has to be 8 digits.";
      } else if (i == 2) {
        item.setProductNumber((long) 11111111);
        message = "Product number already exists.";
      } else if (i == 3) {
        item.setProductNumber((long) 11111112);
        identifiedProductName = null;
        message = "Enter identified product.";
      } else if (i == 4) {
        identifiedProductName = "";
        message = "Empty identified product name.";
      } else if (i == 5) {
        identifiedProductName = "NameTaken3";
        message = "Enter price.";
      } else if (i == 6) {
        item.setPrice((long) 0);
        message = "Price not allowed.";
      }

      assertFalse(itemService.getErrorFlashAttributes(
          identifiedProductName, item, redirectAttributes).isEmpty());

      ArgumentCaptor<String> stringArgument = ArgumentCaptor.forClass(String.class);
      ArgumentCaptor<String> stringArgument2 = ArgumentCaptor.forClass(String.class);
      verify(methodService, atLeast(1))
          .getErrorMessageFlashAttributes(
              stringArgument.capture(),
              any(RedirectAttributes.class),
              stringArgument2.capture());
      verifyNoMoreInteractions(methodService);
      String stringArgumentValue = stringArgument.getValue();
      String stringArgumentValue2 = stringArgument2.getValue();
      assertThat(stringArgumentValue, is(message));
      assertThat(stringArgumentValue2, is("itemError"));
      clearInvocations();
    }
  }

  @Test
  public void getErrorFlashAttributesNotAllowedProductNumber() {
    item.setProductNumber((long) 223);

    when((Object) methodService.getErrorMessageFlashAttributes(
        notNull(),
        notNull(),
        notNull())).thenReturn(errorFlashAttributes);
    assertFalse(itemService.getErrorFlashAttributes(
        identifiedProduct.getName(), item, redirectAttributes).isEmpty());

    ArgumentCaptor<String> stringArgument = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String> stringArgument2 = ArgumentCaptor.forClass(String.class);
    verify(methodService, times(1))
        .getErrorMessageFlashAttributes(
            stringArgument.capture(),
            any(RedirectAttributes.class),
            stringArgument2.capture());
    String stringArgumentValue = stringArgument.getValue();
    String stringArgumentValue2 = stringArgument2.getValue();
    assertThat(stringArgumentValue, containsString("Product number has to be 8 digits."));
    assertThat(stringArgumentValue2, is("itemError"));
  }

//  @Test
//  public void getErrorFlashAttributes() throws Exception {
//    ItemServiceImpl itemServiceMock = spy(new ItemServiceImpl());
//    doReturn(Collections.emptyMap()).when(itemServiceMock,
//        "getErrorMessageFlashAttributes",
//        any(), any());
//    when(itemServiceMock, "getErrorFlashAttributes",
//        any(), any(), any())
//        .thenReturn(Collections.emptyMap());
//
////    assertThat(Whitebox.invokeMethod(new ItemServiceImpl(),
////        "getErrorMessageFlashAttributes", "message", any(RedirectAttributes.class)), instanceOf(Map.class));
//    assertThat(
//        itemService.getErrorFlashAttributes(
//            "name", new Item(), redirectAttributes),
//        is(Collections.emptyMap()));
//  }

}
