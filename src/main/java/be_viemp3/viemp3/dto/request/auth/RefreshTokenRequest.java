package be_viemp3.viemp3.dto.request.auth;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;
}
