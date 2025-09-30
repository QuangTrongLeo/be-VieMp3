package be_viemp3.viemp3.controller.auth;

import be_viemp3.viemp3.dto.request.auth.LoginRequest;
import be_viemp3.viemp3.dto.request.auth.RegisterRequest;
import be_viemp3.viemp3.dto.request.auth.VerifyOtpRequest;
import be_viemp3.viemp3.dto.response.auth.TokenResponse;
import be_viemp3.viemp3.exception.EmailAlreadyExistsException;
import be_viemp3.viemp3.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.vie-mp3-url}/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            authService.register(request);
            return ResponseEntity.ok("OTP đã được gửi tới email: " + request.getEmail());
        } catch (EmailAlreadyExistsException ex) {
            // Email đã tồn tại → trả về 400
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            // Các lỗi khác → trả về 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Đăng ký thất bại: " + ex.getMessage());
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody VerifyOtpRequest request) {
        try {
            authService.verifyOtp(request);
            return ResponseEntity.ok("Xác thực thành công!");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Xác thực thất bại: " + ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            TokenResponse token = authService.login(request);
            return ResponseEntity.ok(token);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            // Lỗi do input sai hoặc trạng thái user chưa hợp lệ
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Đăng nhập thất bại: " + ex.getMessage());
        } catch (Exception ex) {
            // Các lỗi khác (ví dụ DB, JWT, ...)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Có lỗi xảy ra: " + ex.getMessage());
        }
    }
}


