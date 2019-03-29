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


@Configuration
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private static final Logger LOGGER = LoggerFactory.getLogger(WebSecurityConfiguration.class);
/*  private OidcUserService oidcUserService;

  @Autowired
  public WebSecurityConfig(OidcUserService oidcUserService){
    this.oidcUserService = oidcUserService;
  }*/

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
        User user = userRepository.findByEmail(principalEmail);
        if (user == null) {
          LOGGER.info("No user found, access denied");
          return null;
        }
        if (user.getCreated() == null) {
          user.setCreated(LocalDateTime.now());
          user.setLoginType("google");
          user.setIsAdmin(false);
        }

        if (principalEmail.equals(System.getenv("ADMIN_PRESET"))) {
          user.setIsAdmin(true);
        }
        user.setName((String) map.get("name"));
        user.setImageUrl((String) map.get("picture"));
        user.setLastLogin(LocalDateTime.now());

        userRepository.save(user);
        return user;
      };
    }
}
