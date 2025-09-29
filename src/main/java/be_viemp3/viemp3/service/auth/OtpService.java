package be_viemp3.viemp3.service.auth;

import be_viemp3.viemp3.entity.VerificationToken;
import be_viemp3.viemp3.exception.VerifyOtpException;
import be_viemp3.viemp3.repository.auth.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    // TẠO VÀ GỬI OTP
    public void createAndSendOtp(String email) {
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);

        VerificationToken token = createOtp(email, otp);

        verificationTokenRepository.save(token);
        emailService.sendOtp(email, otp);
    }

    // XÁC THỰC OTP
    public void verifyOtp(String email, String otp) {
        VerificationToken token = verificationTokenRepository
                .findByEmailAndOtpAndUsedFalse(email, otp)
                .orElseThrow(() -> new VerifyOtpException("OTP không hợp lệ"));

        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            userService.deleteUserIfNotEnabled(email);
            throw new VerifyOtpException("OTP đã hết hạn");
        }

        token.setUsed(true);
        verificationTokenRepository.save(token);
    }

    // Tạo Otp
    private VerificationToken createOtp(String email, String otp) {
        VerificationToken token = new VerificationToken();
        token.setEmail(email);
        token.setOtp(otp);
        token.setExpiryDate(LocalDateTime.now().plusMinutes(10));
        token.setUsed(false);
        return token;
    }
}

