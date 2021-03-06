package com.devsup.dscatalog.components;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.devsup.dscatalog.entities.User;
import com.devsup.dscatalog.services.UserService;

@Component
public class JwtTokenEnhancer implements TokenEnhancer {

	@Autowired
	private UserService userService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		User user = userService.findByEmail(authentication.getName());
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("userId", user.getId());
		map.put("userFirstName", user.getFirstName());
		
		DefaultOAuth2AccessToken defaultToken = (DefaultOAuth2AccessToken) accessToken;
		defaultToken.setAdditionalInformation(map);
		
		return accessToken;
	}

}
