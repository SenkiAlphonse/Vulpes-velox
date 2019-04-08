package com.vulpes.velox.services;

import com.vulpes.velox.VeloxApplication;
import com.vulpes.velox.models.Item;
import com.vulpes.velox.models.products.IdentifiedProduct;
import com.vulpes.velox.services.identifiedproductservice.IdentifiedProductService;
import com.vulpes.velox.services.itemservice.ItemService;
import com.vulpes.velox.services.methodservice.MethodService;
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

import java.util.*;

import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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
  public void save() {
    assertThat(itemService.getAll().size(), is(1));
    item.setPrice((long) 2);
    itemService.save(item);
    assertThat(itemService.getAll().size(), is(2));
    assertThat(itemService.getAll().get(1).getPrice(), is((long) 2));
    assertThat(itemService.getAll().get(1).getProductNumber(), is(nullValue()));
  }

  @Test
  public void getAll() {
    assertFalse(itemService.getAll().isEmpty());
    assertThat(itemService.getAll().size(), is(1));
    assertThat(itemService.getAll().get(0).getProductNumber(), is((long) 11111111));
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
  public void getNewItemFlashAttributes() {
    when((Object) redirectAttributes.addFlashAttribute(
        notNull(), notNull())).thenReturn(redirectAttributes);
    when((Object) redirectAttributes.getFlashAttributes()).thenReturn(errorFlashAttributes);

    item.setProductNumber((long) 22222222);
    item.setIdentifiedProduct(identifiedProduct);
    assertFalse(itemService.getNewItemFlashAttributes(item, redirectAttributes).isEmpty());

    ArgumentCaptor<String> stringArgument = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String> stringArgument2 = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String> stringArgument3 = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String> stringArgument4 = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String> stringArgument5 = ArgumentCaptor.forClass(String.class);
    verify(redirectAttributes, atLeast(1))
        .addFlashAttribute(
            stringArgument.capture(),
            any(Boolean.class));
    verify(redirectAttributes, atLeast(1))
        .addFlashAttribute(
            stringArgument2.capture(),
            stringArgument3.capture());
    verify(redirectAttributes, atLeast(1))
        .addFlashAttribute(
            stringArgument4.capture(),
            stringArgument5.capture());
    String stringArgumentValue = stringArgument.getValue();
    String stringArgumentValue2 = stringArgument2.getValue();
    String stringArgumentValue3 = stringArgument3.getValue();
    String stringArgumentValue4 = stringArgument2.getValue();
    String stringArgumentValue5 = stringArgument3.getValue();
    assertThat(stringArgumentValue, is("savedItem"));
    assertThat(stringArgumentValue2, containsString(""));
    assertThat(stringArgumentValue3, notNullValue());
    assertThat(stringArgumentValue4, containsString(""));
    assertThat(stringArgumentValue5, notNullValue());

  }




}
