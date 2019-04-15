package com.vulpes.velox.controllers.controlleradvices;

import com.vulpes.velox.controllers.restcontrollers.ApiController;
import com.vulpes.velox.exceptions.runtimeexceptions.BadEmailException;
import com.vulpes.velox.exceptions.runtimeexceptions.BadRequestException;
import com.vulpes.velox.exceptions.runtimeexceptions.ForbiddenException;
import com.vulpes.velox.exceptions.runtimeexceptions.NotFoundException;
import com.vulpes.velox.services.bulkproductservice.BulkProductService;
import com.vulpes.velox.services.identifiedproductservice.IdentifiedProductService;
import com.vulpes.velox.services.productservice.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = ApiController.class, secure = false)
public class ExceptionRestControllerAdviceTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ProductService productService;
  @MockBean
  private IdentifiedProductService identifiedProductService;
  @MockBean
  private BulkProductService bulkProductService;

  @Before
  public void setup() {

  }

  @Test
  public void productsForbidden() throws Exception {
    when(productService.getAll()).thenThrow(new ForbiddenException("Forbidden"));

    mockMvc.perform(get("/api/products")
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isForbidden())
        .andExpect(jsonPath("$.status", is("FORBIDDEN")))
        .andExpect(jsonPath("$.message", is("Forbidden")))
        .andExpect(jsonPath("$.path", is("/api/products")))
        .andExpect(jsonPath("$.timeStamp", containsString("20")));
  }

  @Test
  public void productsNotFound() throws Exception {
    when(productService.getAll()).thenThrow(new NotFoundException("Not found"));

    mockMvc.perform(get("/api/products")
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status", is("NOT_FOUND")))
        .andExpect(jsonPath("$.message", is("Not found")))
        .andExpect(jsonPath("$.path", is("/api/products")))
        .andExpect(jsonPath("$.timeStamp", containsString("20")));
  }

  @Test
  public void productsBadRequest() throws Exception {
    when(productService.getAll()).thenThrow(new BadRequestException("Bad request"));

    mockMvc.perform(get("/api/products")
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status", is("BAD_REQUEST")))
        .andExpect(jsonPath("$.message", is("Bad request")))
        .andExpect(jsonPath("$.path", is("/api/products")))
        .andExpect(jsonPath("$.timeStamp", containsString("20")));
  }

  @Test
  public void productsBadEmail() throws Exception {
    when(productService.getAll()).thenThrow(new BadEmailException("Bad email"));

    mockMvc.perform(get("/api/products")
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status", is("BAD_REQUEST")))
        .andExpect(jsonPath("$.message", is("Bad email")))
        .andExpect(jsonPath("$.path", is("/api/products")))
        .andExpect(jsonPath("$.timeStamp", containsString("20")));
  }

}
