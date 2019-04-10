package com.vulpes.velox.services;

import com.vulpes.velox.VeloxApplication;
import com.vulpes.velox.models.Shipment;
import com.vulpes.velox.models.User;
import com.vulpes.velox.models.products.BulkProduct;
import com.vulpes.velox.services.bulkproductservice.BulkProductService;
import com.vulpes.velox.services.methodservice.MethodService;
import com.vulpes.velox.services.shipmentservice.ShipmentService;
import com.vulpes.velox.services.userservice.UserService;
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

import java.time.LocalDate;
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
public class UserServiceTest {

  @Autowired
  private UserService userService;

  @MockBean
  private MethodService methodService;

  private Map<String, Boolean> errorFlashAttributes;
  private int countAllStart;
  private User user;
  private Shipment shipment;

  @Before
  public void setup() {
    errorFlashAttributes = new HashMap<>();
    errorFlashAttributes.put("itemError", true);
    countAllStart = userService.getAllForPage(1).size();
    user = new User();
    user.setEmail("EmailNew");
  }

  @Test
  public void findByEmail() {
    assertThat(userService.findByEmail("x"), is(nullValue()));
    assertThat(userService.findByEmail("email").getName(), is("user"));
    assertThat(userService.findByEmail("email2").getName(), is("user2"));
  }

  @Test
  public void save() {
    assertThat(userService.getAllForPage(1).size(), is(countAllStart));
    userService.save(user);
    assertThat(userService.getAllForPage(1).size(), is(countAllStart + 1));
  }

  @Test
  public void existsByEmail() {
    assertTrue(userService.existsByEmail("email"));
    assertFalse(userService.existsByEmail("x"));
  }

  @Test
  public void isUser() {

  }







}
