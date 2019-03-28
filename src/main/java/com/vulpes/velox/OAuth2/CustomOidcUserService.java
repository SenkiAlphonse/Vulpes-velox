package com.vulpes.velox.OAuth2;

import com.vulpes.velox.models.User;
import com.vulpes.velox.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomOidcUserService extends OidcUserService {

  private UserRepository userRepository;

  @Autowired
  public CustomOidcUserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
    OidcUser oidcUser = super.loadUser(userRequest);
    Map attributes = oidcUser.getAttributes();
    GoogleOAuth2UserInfo userInfo = new GoogleOAuth2UserInfo();
    userInfo.setEmail((String) attributes.get("email"));
    userInfo.setId((String) attributes.get("sub"));
    userInfo.setImageUrl((String) attributes.get("picture"));
    userInfo.setName((String) attributes.get("name"));
    updateUser(userInfo);
    return oidcUser;
  }

  protected void updateUser(GoogleOAuth2UserInfo userInfo) {
    User user = userRepository.findByEmail(userInfo.getEmail());
    if (user != null) {
      user.setEmail(userInfo.getEmail());
      user.setImageUrl(userInfo.getImageUrl());
      user.setName(userInfo.getName());

      if (user.getEmail().equals(System.getenv("CLIENT_ADMIN"))) {
        user.setIsAdmin(true);
      }
      userRepository.save(user);
    }
  }
}
