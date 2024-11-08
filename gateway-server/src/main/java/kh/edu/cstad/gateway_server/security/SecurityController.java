package kh.edu.cstad.gateway_server.security;

import kh.edu.cstad.gateway_server.dto.UserProfile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class SecurityController {


    @GetMapping("/profile")
    UserProfile secured(@AuthenticationPrincipal Authentication auth) {
        OAuth2AuthenticationToken oauth2 = (OAuth2AuthenticationToken) auth;
        DefaultOidcUser oidcUser = (DefaultOidcUser) oauth2.getCredentials();
        System.out.println(oidcUser.getAttributes());
        return new UserProfile(oidcUser.getName(),
                oidcUser.getUserInfo().getClaimAsString("uuid"),
                oidcUser.getIdToken().getClaimAsString("reksmey1")
        );
    }
}
