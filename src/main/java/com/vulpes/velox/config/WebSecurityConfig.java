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
        authorizeUserByEmailToAdmin(principalEmail);
      }
      if (userByEmail == null) {
        LOGGER.info("No user found, access denied");
        return null;
      }
      updateUserByEmail(map, userRepository);
      return userByEmail;
    };
  }

  private void authorizeUserByEmailToAdmin(String principalEmail) {
    if (userByEmail == null) {
      userByEmail = new User();
      userByEmail.setEmail(principalEmail);
    }
    userByEmail.setIsAdmin(true);
  }

  private void updateUserByEmail(Map<String, Object> map, UserRepository userRepository) {
    if (userByEmail.getCreated() == null) {
      userByEmail.setCreated(LocalDateTime.now());
      userByEmail.setLoginType("google");
    }
    if (userByEmail.getIsAdmin() == null) {
      userByEmail.setIsAdmin(false);
    }
    userByEmail.setName((String) map.get("name"));
    userByEmail.setImageUrl((String) map.get("picture"));
    userByEmail.setLastLogin(LocalDateTime.now());
    userRepository.save(userByEmail);
  }

}
