package be_viemp3.viemp3.service.auth;

import be_viemp3.viemp3.dto.request.auth.RegisterRequest;
import be_viemp3.viemp3.dto.request.auth.VerifyOtpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private OtpService otpService;

    @Autowired
    private UserService userService;

    // Đăng ký
    public void register(RegisterRequest request) {
        userService.createUser(request.getUsername(), request.getEmail(), request.getPassword());
        otpService.createAndSendOtp(request.getEmail());
    }

    // Xác thực OTP
    public void verifyOtp(VerifyOtpRequest request) {
        otpService.verifyOtp(request.getEmail(), request.getOtp());
        userService.enableUser(request.getEmail());
    }
}

