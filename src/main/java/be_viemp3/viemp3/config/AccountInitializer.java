package be_viemp3.viemp3.config;

import be_viemp3.viemp3.entity.Role;
import be_viemp3.viemp3.entity.User;
import be_viemp3.viemp3.enums.RoleEnum;
import be_viemp3.viemp3.repository.user.RoleRepository;
import be_viemp3.viemp3.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(2)
public class AccountInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    // ===== ADMIN =====
    @Value("${admin.username}")
    private String adminUsername;
    @Value("${admin.email}")
    private String adminEmail;
    @Value("${admin.password}")
    private String adminPassword;

    // ===== MOD =====
    @Value("${mod.username}")
    private String modUsername;
    @Value("${mod.email}")
    private String modEmail;
    @Value("${mod.password}")
    private String modPassword;

    @Override
    public void run(String... args) {
        createAccountIfNotExists(adminUsername, adminEmail, adminPassword, RoleEnum.ADMIN);
        createAccountIfNotExists(modUsername, modEmail, modPassword, RoleEnum.MOD);
    }

    private void createAccountIfNotExists(String username, String email, String password, RoleEnum roleEnum) {
        if (userRepository.existsByEmail(email)) return;
        // ===== Lấy hoặc tạo USER role
        Role userRole = roleRepository.findByName(RoleEnum.USER)
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName(RoleEnum.USER);
                    return roleRepository.save(newRole);
                });
        // ===== Lấy hoặc tạo MOD role
        Role modRole = roleRepository.findByName(RoleEnum.MOD)
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName(RoleEnum.MOD);
                    return roleRepository.save(newRole);
                });
        // ===== Lấy hoặc tạo ADMIN role
        Role adminRole = roleRepository.findByName(RoleEnum.ADMIN)
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName(RoleEnum.ADMIN);
                    return roleRepository.save(newRole);
                });

        // ===== Tạo user
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);
        user.getRoles().add(userRole);
        if (roleEnum == RoleEnum.MOD) {
            user.getRoles().add(modRole);
        }
        if (roleEnum == RoleEnum.ADMIN) {
            user.getRoles().add(modRole);
            user.getRoles().add(adminRole);
        }
        userRepository.save(user);
        System.out.println(roleEnum + " account created successfully!");
    }
}