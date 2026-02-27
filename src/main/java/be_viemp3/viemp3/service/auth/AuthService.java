package be_viemp3.viemp3.service.auth;

import be_viemp3.viemp3.dto.request.auth.LoginRequest;
import be_viemp3.viemp3.dto.request.auth.RegisterRequest;
import be_viemp3.viemp3.dto.request.auth.VerifyOtpRequest;
import be_viemp3.viemp3.dto.response.auth.TokenResponse;
import be_viemp3.viemp3.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final OtpService otpService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

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

    // Đăng nhập
    public TokenResponse login(LoginRequest request) {
        User user = userService.findUserByEmail(request.getEmail());

        if (!user.isEnabled()) {
            throw new RuntimeException("Tài khoản chưa kích hoạt!");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Sai mật khẩu!");
        }

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new TokenResponse(accessToken, refreshToken);
    }
}

