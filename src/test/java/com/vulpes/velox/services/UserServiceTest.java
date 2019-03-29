package com.vulpes.velox.services;

import com.vulpes.velox.exceptions.runtimeexceptions.BadRequestException;
import com.vulpes.velox.models.User;
import com.vulpes.velox.services.userservice.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:schema.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:schema.sql")
})
public class UserServiceTest {

  @Autowired
  private UserService userService;

  @Test
  @Transactional
  public void findByEmail_Test() {
    User testUser = userService.findByEmail("user@test.hu");
    assertNotNull(testUser);
  }

  @Test
  @Transactional
  public void findByEmail_Test_Fail() {
    User testUser = userService.findByEmail("user@notexist.noo");
    assertNull(testUser);
  }

  @Test
  @Transactional
  public void userExistsByEmail_Test() {
    boolean testExistence = userService.userExistsByEmail("user@test.hu");
    assertTrue(testExistence);
  }

  @Test
  @Transactional
  public void userExistsByEmail_Test_Fail() {
    boolean testExistence = userService.userExistsByEmail("user@notexist.noo");
    assertFalse(testExistence);
  }

  @Test
  @Transactional
  public void addUser_Test() {
    User testUser = new User();
    testUser.setId(3L);
    testUser.setEmail("dummyuser@test.hu");
    testUser.setIsAdmin(true);
    userService.addUser(testUser);

    List<User> listOfUsers = userService.getAll(0);
    int listOfUsersSize = listOfUsers.size();

    assertEquals(listOfUsersSize, 3);
  }

  @Test(expected = BadRequestException.class)
  @Transactional
  public void addUser_Test_False() {
    User testUser = null;
    userService.addUser(testUser);
  }

  @Test
  @Transactional
  public void findById_Test() {
    User testUser = userService.findById(1L);
    String testEmail = testUser.getEmail();

    assertTrue(testEmail.equalsIgnoreCase("user@test.hu"));
  }

  @Test
  @Transactional
  public void findById_Test_False() {
    User testUser = userService.findById(5L);

    assertNull(testUser);
  }

  @Test
  @Transactional
  public void deleteUserById_Test() {
    userService.deleteUserById(1L);

    List<User> testList = userService.getAll(0);
    int testListSize = testList.size();

    assertEquals(testListSize, 1);
  }

  @Test
  @Transactional
  public void deleteUserById_Test_False() {
    userService.deleteUserById(5L);

    List<User> testList = userService.getAll(0);
    int testListSize = testList.size();

    assertEquals(testListSize, 2);
  }
}
