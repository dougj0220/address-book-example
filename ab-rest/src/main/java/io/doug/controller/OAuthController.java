package io.doug.controller;

import io.doug.entity.User;
import io.doug.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by djeremias on 1/3/17.
 */
@RestController
@RequestMapping("/oauth")
public class OAuthController {
    @Value("${oauth2.client.id}")
    private String getClientId;

    @Autowired
    private TokenStore tokenStore;

    @RequestMapping(value = "/revoke_token", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<String>> revokeToken(@AuthenticationPrincipal User user) {

        if ( user != null ) {

            Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientIdAndUserName(getClientId, user.getLogin());

            for ( OAuth2AccessToken token : tokens ){
                tokenStore.removeAccessToken(token);
            }
        }

        return new ResponseEntity<>(new ApiResponse<>("User has been successfully logged out"), HttpStatus.OK);
    }
}
