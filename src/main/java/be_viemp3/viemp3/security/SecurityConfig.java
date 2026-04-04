package be_viemp3.viemp3.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Value("${api.vie-mp3-url}")
    private String baseUrl;

    // ===== SECURITY FILTER CHAIN =====
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {})
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login/**", "/oauth2/**").permitAll()
                        .requestMatchers(baseUrl + "/ai/**").permitAll()
                        .requestMatchers(baseUrl + "/auth/**").permitAll()
                        .requestMatchers(baseUrl + "/analytics/**").permitAll()
                        .requestMatchers(baseUrl + "/artists/**").permitAll()
                        .requestMatchers(baseUrl + "/albums/**").permitAll()
                        .requestMatchers(baseUrl + "/favorite-artists/**").permitAll()
                        .requestMatchers(baseUrl + "/favorite-albums/**").permitAll()
                        .requestMatchers(baseUrl + "/genres/**").permitAll()
                        .requestMatchers(baseUrl + "/listen-histories/**").permitAll()
                        .requestMatchers(baseUrl + "/playlists/**").permitAll()
                        .requestMatchers(baseUrl + "/songs/**").permitAll()
                        .requestMatchers(baseUrl + "/users/**").permitAll()
                        .requestMatchers(baseUrl + "/notifications/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth -> oauth
                        .successHandler(oAuth2SuccessHandler)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // ===== AUTHENTICATION MANAGER =====
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}