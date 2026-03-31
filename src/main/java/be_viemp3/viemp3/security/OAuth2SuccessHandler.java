package be_viemp3.viemp3.security;

import be_viemp3.viemp3.common.service.EntityQueryService;
import be_viemp3.viemp3.entity.User;
import be_viemp3.viemp3.service.auth.JwtService;
import be_viemp3.viemp3.service.auth.RoleService;
import be_viemp3.viemp3.service.auth.UserService;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final UserService userService;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException {
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");
        String picture = oauthUser.getAttribute("picture");

        User user = userService.processOAuthPostLogin(name, email, picture);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        String domain = "http://localhost:3000";
        String redirectUrl = domain + "/oauth2/redirect"
                + "?accessToken=" + accessToken
                + "&refreshToken=" + refreshToken;

        response.sendRedirect(redirectUrl);
    }
}