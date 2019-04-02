package com.vulpes.velox.config;

import com.vulpes.velox.models.User;
import com.vulpes.velox.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.time.LocalDateTime;
import java.util.Map;


@Configuration
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private static final Logger LOGGER = LoggerFactory.getLogger(WebSecurityConfiguration.class);
  private User userByEmail;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf()
        .disable()
        .antMatcher("/**")
        .authorizeRequests()
        .antMatchers("/", "/login", "/error")
        .permitAll()
        .anyRequest()
        .authenticated();
  }

  @Bean
  public PrincipalExtractor principalExtractor(UserRepository userRepository) {
    return map -> {
      String principalEmail = (String) map.get("email");
      userByEmail = userRepository.findByEmail(principalEmail);
      if (principalEmail.equals(System.getenv("ADMIN_PRESET"))) {
        authorizeUserToAdmin(userByEmail, principalEmail);
      }
      if (userByEmail == null) {
        LOGGER.info("No user found, access denied");
        return null;
      }
      setUserFields(userByEmail, map, userRepository);
      return userByEmail;
    };
  }

  private void authorizeUserToAdmin(User user, String principalEmail) {
    if (user == null){
      user = new User();
      user.setEmail(principalEmail);
    }
    user.setIsAdmin(true);
  }

  private void setUserFields(User user, Map<String, Object> map, UserRepository userRepository) {
    if (user.getCreated() == null) {
      user.setCreated(LocalDateTime.now());
      user.setLoginType("google");
    }
    if (user.getIsAdmin() == null){
      user.setIsAdmin(false);
    }
    user.setName((String) map.get("name"));
    user.setImageUrl((String) map.get("picture"));
    user.setLastLogin(LocalDateTime.now());
    userRepository.save(userByEmail);
  }

}
