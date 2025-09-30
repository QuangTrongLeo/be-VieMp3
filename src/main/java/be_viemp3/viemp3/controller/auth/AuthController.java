package be_viemp3.viemp3.controller.auth;

import be_viemp3.viemp3.dto.request.auth.RegisterRequest;
import be_viemp3.viemp3.dto.request.auth.VerifyOtpRequest;
import be_viemp3.viemp3.exception.EmailAlreadyExistsException;
import be_viemp3.viemp3.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.vie-mp3-url}/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
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
    public ResponseEntity<String> verifyOtp(@RequestBody VerifyOtpRequest request) {
        authService.verifyOtp(request);
        return ResponseEntity.ok("Đăng ký thành công!");
    }
}


