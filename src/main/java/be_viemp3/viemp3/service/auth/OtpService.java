package be_viemp3.viemp3.service.auth;

import be_viemp3.viemp3.entity.VerificationToken;
import be_viemp3.viemp3.exception.VerifyOtpException;
import be_viemp3.viemp3.repository.auth.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskScheduler taskScheduler;

    // TẠO VÀ GỬI OTP
    public void createAndSendOtp(String email) {
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);

        VerificationToken token = createOtp(email, otp);
        verificationTokenRepository.save(token);

        emailService.sendOtp(email, otp);

        // Lên lịch xóa user sau 10 phút nếu chưa xác thực
        taskScheduler.schedule(
                () -> userService.deleteUserIfNotEnabled(email),
                token.getExpiryDate().atZone(ZoneId.systemDefault()).toInstant()
        );
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
        userService.enableUser(email);
    }

    private VerificationToken createOtp(String email, String otp) {
        VerificationToken token = new VerificationToken();
        token.setEmail(email);
        token.setOtp(otp);
        token.setExpiryDate(LocalDateTime.now().plusMinutes(10));
        token.setUsed(false);
        return token;
    }
}

