package be_viemp3.viemp3.service.auth;

import be_viemp3.viemp3.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secretKey}")
    private String jwtSecret;

    @Value("${jwt.accessToken.expiration}")
    private long ACCESS_TOKEN_EXPIRATION;

    @Value("${jwt.refreshToken.expiration}")
    private long REFRESH_TOKEN_EXPIRATION;

    // Tạo Access Token
    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("roles", user.getRoles().stream().map(r -> r.getName().name()).toList())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Tạo Refresh Token
    public String generateRefreshToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Lấy key để ký JWT (dùng Base64 secret)
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret));
    }

    // Xác thực token và lấy email
    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
