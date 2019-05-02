package com.vulpes.velox.services;

import com.vulpes.velox.VeloxApplication;
import com.vulpes.velox.dtos.ProductDto;
import com.vulpes.velox.models.products.IdentifiedProduct;
import com.vulpes.velox.services.identifiedproductservice.IdentifiedProductService;
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

import java.math.BigInteger;
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
public class IdentifiedProductServiceTest {

  @Autowired
  private IdentifiedProductService identifiedProductService;

  @MockBean
  private MethodService methodService;
  @MockBean
  private RedirectAttributes redirectAttributes;

  private Map<String, Boolean> errorFlashAttributes;
  private int countAllStart;
  private ProductDto productDto;
  private IdentifiedProduct identifiedProduct;

  @Before
  public void setup() {
    errorFlashAttributes = new HashMap<>();
    errorFlashAttributes.put("identifiedProductError", true);
    countAllStart = identifiedProductService.getAll().size();
    productDto = new ProductDto();
    productDto.name = "DtoName";
    productDto.quantity = (long) 5;
    identifiedProduct = new IdentifiedProduct();
    identifiedProduct.setName("Name");
    identifiedProduct.setQuantity((long) 15);
  }

  @Test
  public void getAll() {
    assertFalse(identifiedProductService.getAll().isEmpty());
    assertThat(identifiedProductService.getAll().size(), is(2));
    assertThat(identifiedProductService.getAll().get(0).getName(), is("NameTaken3"));
    assertThat(identifiedProductService.getAll().get(0).getQuantity(), is((long) 4));
    assertThat(identifiedProductService.getAll().get(0).getPrice(), is((long) 5));
    assertThat(identifiedProductService.getAll().get(0).getValue(), is(BigInteger.valueOf(6)));
  }

  @Test
  public void getEntityFromDto() {
    assertThat(identifiedProductService.getEntityFromDto(new ProductDto()).getName(), is(nullValue()));
    assertThat(identifiedProductService.getEntityFromDto(productDto).getName(), is("DtoName"));
    assertThat(identifiedProductService.getEntityFromDto(productDto).getQuantity(), is((long) 5));
  }

  @Test
  public void getErrorFlashAttributes() {
    when((Object) methodService.getNameErrorAttributes(
        notNull(),
        notNull(),
        notNull())).thenReturn(errorFlashAttributes);

    assertFalse(identifiedProductService.getErrorFlashAttributes(identifiedProduct, redirectAttributes).isEmpty());

    ArgumentCaptor<IdentifiedProduct> identifiedProductArgument = ArgumentCaptor.forClass(IdentifiedProduct.class);
    ArgumentCaptor<String> stringArgument = ArgumentCaptor.forClass(String.class);
    verify(methodService, times(1))
        .getNameErrorAttributes(
            identifiedProductArgument.capture(),
            any(RedirectAttributes.class),
            stringArgument.capture());
    verifyNoMoreInteractions(methodService);
    assertThat(identifiedProductArgument.getValue().getName(), is("Name"));
    assertThat(identifiedProductArgument.getValue().getQuantity(), is((long) 15));
    assertThat(stringArgument.getValue(), is("identifiedProductError"));
  }

  @Test
  public void getNewIdentifiedProductFlashAttributes() {
    when((Object) redirectAttributes.addFlashAttribute(
        notNull(), notNull())).thenReturn(redirectAttributes);
    when((Object) redirectAttributes.getFlashAttributes()).thenReturn(errorFlashAttributes);

    assertFalse(identifiedProductService.getNewIdentifiedProductFlashAttributes(identifiedProduct, redirectAttributes).isEmpty());

    ArgumentCaptor<String> stringArgument = ArgumentCaptor.forClass(String.class);
    verify(redirectAttributes, atLeast(1))
        .addFlashAttribute(
            stringArgument.capture(),
            any(Boolean.class));
    verify(redirectAttributes, atLeast(1))
        .addFlashAttribute(
            stringArgument.capture(),
            anyString());
    List<String> stringArgumentValue = stringArgument.getAllValues();
    assertThat(stringArgumentValue.get(0), is("savedIdentifiedProduct"));
    assertThat(stringArgumentValue.get(1), is("identifiedProductName"));
  }

  @Test
  public void getAllFilteredBy() {
    assertTrue(identifiedProductService.getAllFilteredBy("x").isEmpty());
    assertThat(identifiedProductService.getAllFilteredBy("Name").size(), is(2));
    assertThat(identifiedProductService.getAllFilteredBy("3").size(), is(1));
    assertThat(identifiedProductService.getAllFilteredBy("Name").get(0).getName(), is("NameTaken3"));
  }

  @Test
  public void saveNewIdentifiedProduct() {
    assertThat(identifiedProductService.getAll().size(), is(countAllStart));
    identifiedProductService.saveNewIdentifiedProduct(identifiedProduct);
    assertThat(identifiedProductService.getAll().size(), is(countAllStart + 1));
    assertThat(identifiedProduct.getQuantity(), is((long) 0));
    assertThat(identifiedProduct.getPrice(), is((long) 0));
    assertThat(identifiedProduct.getValue(), is(BigInteger.valueOf(0)));
    assertThat(identifiedProduct.getUnit(), is("Piece"));
  }

}
