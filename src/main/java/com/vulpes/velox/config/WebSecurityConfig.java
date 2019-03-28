package com.vulpes.velox.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

@Configuration
@EnableWebSecurity(debug = true)
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private OidcUserService oidcUserService;

  @Autowired
  public WebSecurityConfig(OidcUserService oidcUserService){
    this.oidcUserService = oidcUserService;
  }
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf()
        .disable()
        .antMatcher("/**")
        .authorizeRequests()
        .antMatchers("/")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .oauth2Login()
        .userInfoEndpoint()
        .oidcUserService(oidcUserService)
        .and()
        .authorizationEndpoint()
        .authorizationRequestRepository(customAuthorizationRequestRepository());
  }

  @Bean
  public AuthorizationRequestRepository<OAuth2AuthorizationRequest> customAuthorizationRequestRepository() {
    return new HttpSessionOAuth2AuthorizationRequestRepository();
  }
}
