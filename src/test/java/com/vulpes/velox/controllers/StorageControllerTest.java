package com.vulpes.velox.controllers;

import com.vulpes.velox.services.bulkproductservice.BulkProductService;
import com.vulpes.velox.services.identifiedproductservice.IdentifiedProductService;
import com.vulpes.velox.services.userservice.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = StorageController.class, secure = false)
public class StorageControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private IdentifiedProductService identifiedProductService;
  @MockBean
  private BulkProductService bulkProductService;
  @MockBean
  private UserService userService;

  @Before
  public void setup() {
  }

  @Test
  public void storageAddWithFilter() throws Exception {
    when(userService.isUser(isNull())).thenReturn(true);
    when(bulkProductService.getAllFilteredBy("filter")).thenReturn(Collections.emptyList());
    when(identifiedProductService.getAllFilteredBy("filter")).thenReturn(Collections.emptyList());

    mockMvc.perform(get("/storage/add")
        .param("filterProducts", "filter")
    )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(model().attribute("identifiedProductsFiltered", Collections.emptyList()))
        .andExpect(model().attribute("bulkProductsFiltered", Collections.emptyList()))
        .andExpect(view().name("addProducts"));

    verify(userService, times(1)).isUser(isNull());
    verifyNoMoreInteractions(userService);

    verify(bulkProductService, times(1)).getAllFilteredBy("filter");
    verifyNoMoreInteractions(bulkProductService);

    verify(identifiedProductService, times(1)).getAllFilteredBy("filter");
    verifyNoMoreInteractions(identifiedProductService);

  }

  @Test
  public void storageAddWithoutFilter() throws Exception {
    when(userService.isUser(isNull())).thenReturn(true);
    when(bulkProductService.getAll()).thenReturn(Collections.emptyList());
    when(identifiedProductService.getAll()).thenReturn(Collections.emptyList());

    mockMvc.perform(get("/storage/add"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(model().attribute("identifiedProductsFiltered", Collections.emptyList()))
        .andExpect(model().attribute("bulkProductsFiltered", Collections.emptyList()))
        .andExpect(view().name("addProducts"));

    verify(userService, times(1)).isUser(isNull());
    verifyNoMoreInteractions(userService);

    verify(bulkProductService, times(1)).getAll();
    verifyNoMoreInteractions(bulkProductService);

    verify(identifiedProductService, times(1)).getAll();
    verifyNoMoreInteractions(identifiedProductService);
  }

  @Test
  public void storageAddNotAuthenticated() throws Exception {
    when(userService.isUser(isNull())).thenReturn(false);
    when(userService.getUserEmail(isNull())).thenReturn("email");

    mockMvc.perform(get("/storage/add")
        .param("filterProducts", "filter")
    )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(model().attribute("unauthorizedEmail", "email"))
        .andExpect(view().name("unauthorized"));

    verify(userService, times(1)).isUser(isNull());
    verify(userService, times(1)).getUserEmail(isNull());
    verifyNoMoreInteractions(userService);
    verifyZeroInteractions(identifiedProductService);
    verifyZeroInteractions(bulkProductService);
  }

}
