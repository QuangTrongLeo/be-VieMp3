package be_viemp3.viemp3.service.auth;

import be_viemp3.viemp3.entity.Role;
import be_viemp3.viemp3.entity.User;
import be_viemp3.viemp3.enums.RoleEnum;
import be_viemp3.viemp3.common.exception.EmailAlreadyExistsException;
import be_viemp3.viemp3.repository.user.RoleRepository;
import be_viemp3.viemp3.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Tạo user chưa kích hoạt
    public void createUser(String username, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("Tài khoản này đã tồn tại!");
        }
        User user = buildUser(username, email, password);
        userRepository.save(user);
    }

    // kích hoạt user khi OTP hợp lệ
    public void enableUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user với email: " + email));
        user.setEnabled(true);
        userRepository.save(user);
    }

    // xóa user chưa kích hoạt (dọn dẹp sau 10 phút)
    public void deleteUserIfNotEnabled(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            if (!user.isEnabled()) {
                userRepository.delete(user);
            }
        });
    }

    public User findUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email không tồn tại!"));
    }

    // Tạo user
    private User buildUser(String username, String email, String password){
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(false);

        Role roleUser = roleRepository.findByName(RoleEnum.USER)
                .orElseThrow(() -> new RuntimeException("Role USER không tồn tại!"));
        user.getRoles().add(roleUser);
        return user;
    }
}

