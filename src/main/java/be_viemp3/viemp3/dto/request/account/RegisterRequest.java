package be_viemp3.viemp3.dto.request.account;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
}
