package com.vulpes.velox.services;

import com.vulpes.velox.VeloxApplication;
import com.vulpes.velox.exceptions.runtimeexceptions.BadRequestException;
import com.vulpes.velox.models.Shipment;
import com.vulpes.velox.models.User;
import com.vulpes.velox.services.methodservice.MethodService;
import com.vulpes.velox.services.userservice.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.notNull;
import static org.hamcrest.Matchers.*;
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
  @MockBean
  private OAuth2Authentication authentication;
  @MockBean
  private RedirectAttributes redirectAttributes;

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
    assertThat(userService.getByEmail("x"), is(nullValue()));
    assertThat(userService.getByEmail("email").getName(), is("user"));
    assertThat(userService.getByEmail("email2").getName(), is("user2"));
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
  public void getAllForPage() {
    assertFalse(userService.getAllForPage(1).isEmpty());
    assertThat(userService.getAllForPage(1).get(0).getEmail(), is("email8"));
    assertThat(userService.getAllForPage(1).get(1).getEmail(), is("email9"));
  }

  @Test(expected = BadRequestException.class)
  public void addUserException() {
    userService.addUser(null);
  }

  @Test
  public void addUser() {
    assertThat(userService.getAllForPage(1).size(), is(countAllStart));
    userService.addUser(user);
    assertThat(userService.getAllForPage(1).size(), is(countAllStart + 1));
  }

  @Test
  public void deleteUserById() {
    assertThat(userService.getAllForPage(1).size(), is(countAllStart));
    userService.deleteUserById(null);
    assertThat(userService.getAllForPage(1).size(), is(countAllStart));
    userService.deleteUserById(userService.getAllForPage(1).get(1).getId());
    assertThat(userService.getAllForPage(1).size(), is(countAllStart - 1));
  }

  @Test
  public void getById() {
    assertThat(userService.getById(
        userService.getAllForPage(1).get(0).getId()).getName(),
        is("user8"));
    assertThat(userService.getById(
        userService.getAllForPage(1).get(1).getId()).getName(),
        is("user9"));
  }

  @Test
  public void getErrorFlashAttributes() {
    user = null;
    String message = "Error creating user.";

    when((Object) methodService.getErrorMessageFlashAttributes(
        notNull(),
        notNull(),
        notNull())).thenReturn(errorFlashAttributes);

    for (int i = 0; i < 3; i++) {
      if (i == 1) {
        user = new User();
        message = "Enter an e-mail address.";
      } else if (i == 2) {
        user.setEmail("email");
        message = "This e-mail address already exists in the database.";
      }

      assertFalse(userService.getErrorFlashAttributes(user, redirectAttributes).isEmpty());

      ArgumentCaptor<String> stringArgument = ArgumentCaptor.forClass(String.class);
      ArgumentCaptor<String> stringArgument2 = ArgumentCaptor.forClass(String.class);
      verify(methodService, atLeast(1))
          .getErrorMessageFlashAttributes(
              stringArgument.capture(),
              any(RedirectAttributes.class),
              stringArgument2.capture());
      verifyNoMoreInteractions(methodService);
      assertThat(stringArgument.getValue(), is(message));
      assertThat(stringArgument2.getValue(), is("userError"));
      clearInvocations();
    }
  }




}
