package com.vulpes.velox.controllers;

import com.vulpes.velox.models.products.IdentifiedProduct;
import com.vulpes.velox.services.identifiedproductservice.IdentifiedProductService;
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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = IdentifiedProductController.class, secure = false)
public class IdentifiedProductControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private IdentifiedProductService identifiedProductService;

  @MockBean
  private UserService userService;

  private Map<String, Boolean> errorFlashAttributes;

  @Before
  public void setup() {
    errorFlashAttributes = new HashMap<>();
    errorFlashAttributes.put("itemError", true);
  }

  @Test
  public void identifiedProductNewOk() throws Exception {
    when(userService.isUser(isNull())).thenReturn(true);
    when(identifiedProductService.getErrorFlashAttributes(
        notNull(), notNull())).thenReturn(Collections.emptyMap());

    mockMvc.perform(post("/identifiedProduct/new")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("id", "1")
        .param("name", "Name")
    )
        .andDo(print())
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/storage/add"))
        .andExpect(view().name("redirect:/storage/add"));

    verify(userService, times(1)).isUser(isNull());
    verifyNoMoreInteractions(userService);

    ArgumentCaptor<IdentifiedProduct> identifiedProductArgument = ArgumentCaptor.forClass(IdentifiedProduct.class);
    verify(identifiedProductService, times(1))
        .saveNewIdentifiedProduct(identifiedProductArgument.capture());
    verify(identifiedProductService, times(1))
        .getNewIdentifiedProductFlashAttributes(any(IdentifiedProduct.class), any(RedirectAttributes.class));
    verify(identifiedProductService, times(1))
        .getErrorFlashAttributes(any(IdentifiedProduct.class), any(RedirectAttributes.class));
    verifyNoMoreInteractions(identifiedProductService);

    IdentifiedProduct identifiedProductArgumentValue = identifiedProductArgument.getValue();
    assertThat(identifiedProductArgumentValue.getId(), is((long) 1));
    assertThat(identifiedProductArgumentValue.getName(), is("Name"));
  }

  @Test
  public void identifiedProductNewWithoutName() throws Exception {
    when(userService.isUser(isNull())).thenReturn(true);
    when((Object) identifiedProductService.getErrorFlashAttributes(
        notNull(), notNull())).thenReturn(errorFlashAttributes);

    mockMvc.perform(post("/identifiedProduct/new")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("id", "1")
    )
        .andDo(print())
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/storage/add"))
        .andExpect(view().name("redirect:/storage/add"));

    ArgumentCaptor<IdentifiedProduct> identifiedProductArgument = ArgumentCaptor.forClass(IdentifiedProduct.class);

    verify(identifiedProductService, times(1)).getErrorFlashAttributes(
        identifiedProductArgument.capture(), any(RedirectAttributes.class));
    verifyNoMoreInteractions(identifiedProductService);

    IdentifiedProduct identifiedProductArgumentValue = identifiedProductArgument.getValue();
    assertThat(identifiedProductArgumentValue.getId(), is((long) 1));
    assertThat(identifiedProductArgumentValue.getName(), is(nullValue()));
  }

  @Test
  public void identifiedProductNewNotAuthenticated() throws Exception {
    when(userService.isUser(isNull())).thenReturn(false);

    mockMvc.perform(post("/identifiedProduct/new")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("id", "1")
        .param("name", "Name")
    )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("unauthorized"));

    verify(userService, times(1)).isUser(isNull());
    verify(userService, times(1)).getUserEmail(isNull());
    verifyNoMoreInteractions(userService);
  }



}
