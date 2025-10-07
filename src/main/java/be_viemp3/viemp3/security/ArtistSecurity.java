package be_viemp3.viemp3.security;

import be_viemp3.viemp3.service.auth.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Configuration
public class ArtistSecurity {

    @Value("${api.vie-mp3-url}")
    private String apiVieMp3Url;

    @Autowired
    private JwtService jwtService;

    public class JwtArtistFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request,
                                        HttpServletResponse response,
                                        FilterChain filterChain)
                throws ServletException, IOException {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                try {
                    String email = jwtService.extractEmail(token);
                    List<String> roles = jwtService.extractRoles(token);

                    // convert roles -> GrantedAuthority
                    List<SimpleGrantedAuthority> authorities = roles.stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                            .toList();

                    // tạo Authentication có roles
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(email, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                } catch (ExpiredJwtException e) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
                    return;
                } catch (Exception e) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                    return;
                }
            }
            filterChain.doFilter(request, response);
        }
    }

    @Bean
    public SecurityFilterChain securityFilterChainArtist(HttpSecurity http) throws Exception {
        return http
                .securityMatcher(apiVieMp3Url + "/artists/**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Cho phép tất cả (kể cả chưa đăng nhập) gọi GET
                        .requestMatchers(HttpMethod.GET, apiVieMp3Url + "/artists/**").permitAll()

                        // MOD + ADMIN được PUT
                        .requestMatchers(HttpMethod.PUT, apiVieMp3Url + "/artists/**").hasAnyRole("MOD", "ADMIN")

                        // ADMIN có toàn quyền POST + DELETE
                        .requestMatchers(HttpMethod.POST, apiVieMp3Url + "/artists/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, apiVieMp3Url + "/artists/**").hasRole("ADMIN")

                        // Các request khác phải xác thực
                        .anyRequest().authenticated()
                )
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtArtistFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

//    @Bean
//    public AuthenticationManager authenticationManagerArtist(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
}
