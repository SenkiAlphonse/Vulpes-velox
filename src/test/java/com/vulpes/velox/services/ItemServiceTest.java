package com.vulpes.velox.services;

import com.vulpes.velox.VeloxApplication;
import com.vulpes.velox.models.Item;
import com.vulpes.velox.models.products.IdentifiedProduct;
import com.vulpes.velox.repositories.ItemRepository;
import com.vulpes.velox.services.identifiedproductservice.IdentifiedProductService;
import com.vulpes.velox.services.itemservice.ItemService;
import com.vulpes.velox.services.itemservice.ItemServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.reflect.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.when;
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
  private RedirectAttributes redirectAttributes;

  private List<IdentifiedProduct> identifiedProducts;
  private IdentifiedProduct identifiedProduct;
  private Map<String, Boolean> map;


  @Before
  public void setup() {
    identifiedProducts = identifiedProductService.getAll();
    identifiedProduct = identifiedProducts.get(0);
  }

  @Test
  public void getAllByIdentifiedProduct() {
    assertThat(itemService.getAllByIdentifiedProduct(identifiedProduct).size(), is(1));
    assertThat(itemService.getAllByIdentifiedProduct(identifiedProduct), instanceOf(List.class));
    assertThat(itemService.getAllByIdentifiedProduct(identifiedProduct).get(0).getProductNumber(), is((long) 11111111));
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
