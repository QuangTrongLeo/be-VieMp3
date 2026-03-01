package be_viemp3.viemp3.service.auth;

import be_viemp3.viemp3.common.util.SecurityUtils;
import be_viemp3.viemp3.dto.request.auth.RegisterRequest;
import be_viemp3.viemp3.dto.request.auth.UpdateProfileRequest;
import be_viemp3.viemp3.dto.response.auth.UserResponse;
import be_viemp3.viemp3.entity.Role;
import be_viemp3.viemp3.entity.User;
import be_viemp3.viemp3.enums.RoleEnum;
import be_viemp3.viemp3.common.exception.EmailAlreadyExistsException;
import be_viemp3.viemp3.mapper.auth.UserMapper;
import be_viemp3.viemp3.repository.auth.RoleRepository;
import be_viemp3.viemp3.repository.auth.UserRepository;
import be_viemp3.viemp3.service.file.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SecurityUtils securityUtils;
    @Autowired
    private FileStorageService fileStorageService;

    // Tạo user chưa kích hoạt
    public void createUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Tài khoản này đã tồn tại!");
        }
        User user = buildUser(request.getUsername(), request.getEmail(), request.getPassword());
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

    // ===== GET PROFILE =====
    public UserResponse getMyProfile() {
        User currentUser = securityUtils.getCurrentUser();
        return UserMapper.toResponse(currentUser);
    }

    // ===== UPDATE PROFILE =====
    public UserResponse updateProfile(UpdateProfileRequest request) {
        User currentUser = securityUtils.getCurrentUser();
        boolean isUpdated = false;
        // update username
        if (request.getUsername() != null && !request.getUsername().isBlank()) {
            currentUser.setUsername(request.getUsername().trim());
            isUpdated = true;
        }
        // update avatar
        if (request.getAvatar() != null && !request.getAvatar().isEmpty()) {
            // xóa avatar cũ nếu có
            if (currentUser.getAvatar() != null && !currentUser.getAvatar().isBlank()) {
                fileStorageService.deleteByUrl(currentUser.getAvatar());
            }
            String newAvatar = fileStorageService.upload(request.getAvatar(), "avatars");
            currentUser.setAvatar(newAvatar);
            isUpdated = true;
        }
        if (!isUpdated) {
            throw new IllegalArgumentException("Không có dữ liệu để cập nhật");
        }
        userRepository.save(currentUser);
        return UserMapper.toResponse(currentUser);
    }

    // ===== GET ALL USER =====
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return UserMapper.toResponseList(users);
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

