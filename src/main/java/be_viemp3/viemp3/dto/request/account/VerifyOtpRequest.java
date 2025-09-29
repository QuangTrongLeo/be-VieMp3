package be_viemp3.viemp3.dto.request.account;

import lombok.Data;

@Data
public class VerifyOtpRequest {
    private String email;
    private String otp;
}
