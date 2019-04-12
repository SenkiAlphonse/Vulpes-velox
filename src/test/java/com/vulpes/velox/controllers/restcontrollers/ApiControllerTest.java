package com.vulpes.velox.controllers.restcontrollers;

import com.vulpes.velox.TestService;
import com.vulpes.velox.controllers.restcontrollers.ApiController;
import com.vulpes.velox.dtos.ProductDto;
import com.vulpes.velox.models.products.BulkProduct;
import com.vulpes.velox.models.products.IdentifiedProduct;
import com.vulpes.velox.services.bulkproductservice.BulkProductService;
import com.vulpes.velox.services.identifiedproductservice.IdentifiedProductService;
import com.vulpes.velox.services.productservice.ProductService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ApiController.class, secure = false)
public class ApiControllerTest {


  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ProductService productService;
  @MockBean
  private IdentifiedProductService identifiedProductService;
  @MockBean
  private BulkProductService bulkProductService;

  private List<ProductDto> productDtos;
  private ProductDto productDto;
  private ProductDto productDto2;

  @Before
  public void setup() {
    productDto = new ProductDto();
    productDto.name = "Name";
    productDto.quantity = (long) 5;
    productDto2 = new ProductDto();
    productDto2.name = "Name2";
    productDto2.quantity = (long) 10;

    productDtos = Arrays.asList(productDto, productDto2);
  }

  @Test
  public void productsOk() throws Exception {
    when(productService.getAll()).thenReturn(Collections.emptyList());
    when(productService.getDtosFromEntities(Collections.emptyList())).thenReturn(productDtos);

    mockMvc.perform(get("/api/products")
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", Matchers.hasSize(2)))
        .andExpect(jsonPath("$[0].name", is("Name")))
        .andExpect(jsonPath("$[0].quantity", is(5)))
        .andExpect(jsonPath("$[1].name", is("Name2")))
        .andExpect(jsonPath("$[1].quantity", is(10)));
  }

  @Test
  public void productsNotFound() throws Exception {
    when(productService.getAll()).thenReturn(Collections.emptyList());
    when(productService.getDtosFromEntities(Collections.emptyList())).thenReturn(null);

    mockMvc.perform(get("/api/products")
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.status", is("INTERNAL_SERVER_ERROR")))
        .andExpect(jsonPath("$.message", is("Can't find products")))
        .andExpect(jsonPath("$.path", is("/api/products")))
        .andExpect(jsonPath("$.timeStamp", containsString("20")));
  }

  @Test
  public void productsEmpty() throws Exception {
    when(productService.getAll()).thenReturn(Collections.emptyList());
    when(productService.getDtosFromEntities(Collections.emptyList())).thenReturn(Collections.emptyList());

    mockMvc.perform(get("/api/products")
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.status", is("INTERNAL_SERVER_ERROR")))
        .andExpect(jsonPath("$.message", is("Can't find products")))
        .andExpect(jsonPath("$.path", is("/api/products")))
        .andExpect(jsonPath("$.timeStamp", containsString("20")));
  }

  @Test
  public void newIdentifiedProductOk() throws Exception {
    when(identifiedProductService.getEntityFromDto(notNull())).thenReturn(new IdentifiedProduct());

    mockMvc.perform(post("/api/product/identified")
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestService.convertToJson(productDto))
    )
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void newIdentifiedProductEmptyDto() throws Exception {
    mockMvc.perform(post("/api/product/identified")
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestService.convertToJson(new ProductDto()))
    )
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message[0]").isNotEmpty())
        .andExpect(jsonPath("$.message[1]").isNotEmpty());
  }

  @Test
  public void newIdentifiedProductWithoutDto() throws Exception {
    mockMvc.perform(post("/api/product/identified")
        .contentType(MediaType.APPLICATION_JSON)
    )
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  public void newBulkProductOk() throws Exception {
    when(bulkProductService.getEntityFromDto(notNull())).thenReturn(new BulkProduct());

    mockMvc.perform(post("/api/product/bulk")
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestService.convertToJson(productDto))
    )
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void newBulkProductEmptyDto() throws Exception {
    mockMvc.perform(post("/api/product/bulk")
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestService.convertToJson(new ProductDto()))
    )
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message[0]").isNotEmpty())
        .andExpect(jsonPath("$.message[1]").isNotEmpty());
  }

  @Test
  public void newBulkProductWithoutDto() throws Exception {
    mockMvc.perform(post("/api/product/bulk")
        .contentType(MediaType.APPLICATION_JSON)
    )
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

}
