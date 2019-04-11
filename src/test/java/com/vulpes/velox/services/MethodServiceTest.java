package com.vulpes.velox.services;

import com.vulpes.velox.VeloxApplication;
import com.vulpes.velox.models.products.BulkProduct;
import com.vulpes.velox.models.products.IdentifiedProduct;
import com.vulpes.velox.models.products.Product;
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
public class MethodServiceTest {

  @Autowired
  private MethodService methodService;

  @MockBean
  private RedirectAttributes redirectAttributes;

  private Map<String, Boolean> errorFlashAttributes;

  @Before
  public void setup() {
    errorFlashAttributes = new HashMap<>();
    errorFlashAttributes.put("errorAttribute", true);
  }

  @Test
  public void getErrorMessageFlashAttributesWithMessage() {
    when((Object) redirectAttributes.addFlashAttribute(
        notNull(), notNull())).thenReturn(redirectAttributes);
    when((Object) redirectAttributes.getFlashAttributes()).thenReturn(errorFlashAttributes);

    assertFalse(methodService.getErrorMessageFlashAttributes(
        "Message", redirectAttributes, "ErrorAttribute").isEmpty());

    ArgumentCaptor<String> stringArgument = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String> stringArgument2 = ArgumentCaptor.forClass(String.class);
    verify(redirectAttributes, times(1))
        .addFlashAttribute(
            stringArgument.capture(),
            any(Boolean.class));
    verify(redirectAttributes, times(2))
        .addFlashAttribute(
            anyString(),
            stringArgument2.capture());
    verify(redirectAttributes, times(1))
        .getFlashAttributes();
    String stringArgumentValue = stringArgument.getValue();
    String stringArgumentValue2 = stringArgument2.getValue();
    assertThat(stringArgumentValue, is("ErrorAttribute"));
    assertThat(stringArgumentValue2, is("Message"));

    assertFalse(methodService.getErrorMessageFlashAttributes(
        "", redirectAttributes, "ErrorAttribute").isEmpty());

    verify(redirectAttributes, times(2))
        .getFlashAttributes();
    verifyNoMoreInteractions(redirectAttributes);
  }

//  @Test
//  public void getErrorMessageFlashAttributesEmptyMessage() {
//    when((Object) redirectAttributes.addFlashAttribute(
//        notNull(), notNull())).thenReturn(redirectAttributes);
//    when((Object) redirectAttributes.getFlashAttributes()).thenReturn(errorFlashAttributes);
//
//    assertFalse(methodService.getErrorMessageFlashAttributes(
//        "", redirectAttributes, "ErrorAttribute").isEmpty());
//
//    verify(redirectAttributes, times(1))
//        .getFlashAttributes();
//    verifyNoMoreInteractions(redirectAttributes);
//  }

  @Test
  public void getNameErrorAttributes() {
    Product product = new BulkProduct();
    String message = "Enter bulk product name.";

    when((Object) redirectAttributes.addFlashAttribute(
        notNull(), notNull())).thenReturn(redirectAttributes);
    when((Object) redirectAttributes.getFlashAttributes()).thenReturn(errorFlashAttributes);

    for (int i = 0; i < 6; i++) {
      if (i == 1) {
        product.setName("");
        message = "Empty bulk product name.";
      } else if (i == 2) {
        product.setName("NameTaken");
        message = "Product name already exists.";
      } else if (i == 3) {
        product = new IdentifiedProduct();
        product.setName(null);
        message = "Enter identified product name.";
      } else if (i == 4) {
        product.setName("");
        message = "Empty identified product name.";
      } else if (i == 5) {
        product.setName("NameTaken");
        message = "Product name already exists.";
      }

      assertFalse(methodService.getNameErrorAttributes(
          product, redirectAttributes, "ErrorAttribute").isEmpty());

      ArgumentCaptor<String> stringArgument = ArgumentCaptor.forClass(String.class);
      ArgumentCaptor<String> stringArgument2 = ArgumentCaptor.forClass(String.class);
      verify(redirectAttributes, atLeast(1))
          .addFlashAttribute(
              stringArgument.capture(),
              any(Boolean.class));
      verify(redirectAttributes, atLeast(2))
          .addFlashAttribute(
              anyString(),
              stringArgument2.capture());
      verify(redirectAttributes, atLeast(1))
          .getFlashAttributes();
      String stringArgumentValue = stringArgument.getValue();
      String stringArgumentValue2 = stringArgument2.getValue();
      assertThat(stringArgumentValue, is("ErrorAttribute"));
      assertThat(stringArgumentValue2, is(message));
      clearInvocations();
    }
  }

}
