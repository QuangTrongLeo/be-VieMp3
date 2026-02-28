package be_viemp3.viemp3.repository.auth;

import be_viemp3.viemp3.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, String> {
    Optional<VerificationToken> findByEmailAndOtpAndUsedFalse(String email, String otp);
}
