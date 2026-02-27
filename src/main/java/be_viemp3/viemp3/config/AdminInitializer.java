package be_viemp3.viemp3.config;

import be_viemp3.viemp3.entity.Role;
import be_viemp3.viemp3.entity.User;
import be_viemp3.viemp3.enums.RoleEnum;
import be_viemp3.viemp3.repository.user.RoleRepository;
import be_viemp3.viemp3.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) throws Exception {

        // Nếu đã tồn tại email thì không tạo lại
        if (userRepository.existsByEmail(adminEmail)) {
            return;
        }

        // Lấy role ADMIN
        Role roleAdmin = roleRepository.findByName(RoleEnum.ADMIN)
                .orElseThrow(() -> new RuntimeException("Role ADMIN không tồn tại!"));

        User admin = new User();
        admin.setUsername(adminUsername);
        admin.setEmail(adminEmail);
        admin.setPassword(passwordEncoder.encode(adminPassword));
        admin.setEnabled(true);
        admin.getRoles().add(roleAdmin);

        userRepository.save(admin);

        System.out.println("ADMIN account created successfully!");
    }
}
