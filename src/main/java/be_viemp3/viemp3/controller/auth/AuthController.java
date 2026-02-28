package be_viemp3.viemp3.controller.auth;

import be_viemp3.viemp3.common.response.ApiResponse;
import be_viemp3.viemp3.dto.request.auth.LoginRequest;
import be_viemp3.viemp3.dto.request.auth.RegisterRequest;
import be_viemp3.viemp3.dto.request.auth.VerifyOtpRequest;
import be_viemp3.viemp3.dto.response.auth.TokenResponse;
import be_viemp3.viemp3.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.vie-mp3-url}/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // ===== REGISTER =====
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("OTP đã được gửi tới email: " + request.getEmail())
                        .build()
        );
    }

    // ===== VERIFY OTP =====
    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<Void>> verifyOtp(@RequestBody VerifyOtpRequest request) {
        authService.verifyOtp(request);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Xác thực tài khoản thành công")
                        .build()
        );
    }

    // ===== LOGIN =====
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(@RequestBody LoginRequest request) {
        TokenResponse tokenResponse = authService.login(request);
        return ResponseEntity.ok(
                ApiResponse.<TokenResponse>builder()
                        .success(true)
                        .message("Đăng nhập thành công")
                        .data(tokenResponse)
                        .build()
        );
    }
}