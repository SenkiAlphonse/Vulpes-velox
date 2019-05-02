package com.vulpes.velox.services;

import com.vulpes.velox.VeloxApplication;
import com.vulpes.velox.dtos.ProductDto;
import com.vulpes.velox.models.products.BulkProduct;
import com.vulpes.velox.services.bulkproductservice.BulkProductService;
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
public class BulkProductServiceTest {

  @Autowired
  private BulkProductService bulkProductService;

  @MockBean
  private MethodService methodService;
  @MockBean
  private RedirectAttributes redirectAttributes;

  private Map<String, Boolean> errorFlashAttributes;
  private ProductDto productDto;
  private BulkProduct bulkProduct;
  private int countAllStart;

  @Before
  public void setup() {
    errorFlashAttributes = new HashMap<>();
    errorFlashAttributes.put("bulkProductError", true);
    productDto = new ProductDto();
    productDto.name = "DtoName";
    productDto.quantity = (long) 5;
    bulkProduct = new BulkProduct();
    bulkProduct.setName("Name");
    bulkProduct.setQuantity((long) 15);
    countAllStart = bulkProductService.getAll().size();
  }

  @Test
  public void getAll() {
    assertFalse(bulkProductService.getAll().isEmpty());
    assertThat(bulkProductService.getAll().size(), is(2));
    assertThat(bulkProductService.getAll().get(0).getName(), is("NameTaken"));
  }

  @Test
  public void getEntityFromDto() {
    assertThat(bulkProductService.getEntityFromDto(new ProductDto()).getName(), is(nullValue()));
    assertThat(bulkProductService.getEntityFromDto(productDto).getName(), is("DtoName"));
    assertThat(bulkProductService.getEntityFromDto(productDto).getQuantity(), is((long) 5));
  }

  @Test
  public void existsByName() {
    assertTrue(bulkProductService.existsByName("NameTaken"));
    assertFalse(bulkProductService.existsByName("NameNew"));
  }

  @Test
  public void getErrorFlashAttributes() {
    when((Object) methodService.getNameErrorAttributes(
        notNull(),
        notNull(),
        notNull())).thenReturn(errorFlashAttributes);

    assertFalse(bulkProductService.getErrorFlashAttributes(bulkProduct, redirectAttributes).isEmpty());

    ArgumentCaptor<BulkProduct> bulkProductArgument = ArgumentCaptor.forClass(BulkProduct.class);
    ArgumentCaptor<String> stringArgument = ArgumentCaptor.forClass(String.class);
    verify(methodService, times(1))
        .getNameErrorAttributes(
            bulkProductArgument.capture(),
            any(RedirectAttributes.class),
            stringArgument.capture());
    verifyNoMoreInteractions(methodService);
    assertThat(bulkProductArgument.getValue().getName(), is("Name"));
    assertThat(bulkProductArgument.getValue().getQuantity(), is((long) 15));
    assertThat(stringArgument.getValue(), is("bulkProductError"));
  }

  @Test
  public void getNewBulkProductFlashAttributes() {
    when((Object) redirectAttributes.addFlashAttribute(
        notNull(), notNull())).thenReturn(redirectAttributes);
    when((Object) redirectAttributes.getFlashAttributes()).thenReturn(errorFlashAttributes);

    assertFalse(bulkProductService.getNewBulkProductFlashAttributes(bulkProduct, redirectAttributes).isEmpty());

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
    assertThat(stringArgumentValue.get(0), is("savedBulkProduct"));
    assertThat(stringArgumentValue.get(1), is("bulkProductName"));
  }

  @Test
  public void getAllFilteredBy() {
    assertTrue(bulkProductService.getAllFilteredBy("x").isEmpty());
    assertThat(bulkProductService.getAllFilteredBy("Name").size(), is(2));
    assertThat(bulkProductService.getAllFilteredBy("2").size(), is(1));
    assertThat(bulkProductService.getAllFilteredBy("Name").get(0).getName(), is("NameTaken"));
  }

  @Test
  public void saveNewBulkProduct() {
    assertThat(bulkProductService.getAll().size(), is(countAllStart));
    bulkProductService.saveNewBulkProduct(bulkProduct, "Unit");
    assertThat(bulkProductService.getAll().size(), is(countAllStart + 1));
    assertThat(bulkProduct.getQuantity(), is((long) 0));
    assertThat(bulkProduct.getPrice(), is((long) 0));
    assertThat(bulkProduct.getValue(), is(BigInteger.valueOf(0)));
    assertThat(bulkProduct.getUnit(), is("Unit"));
  }

}




