package be_viemp3.viemp3.service.auth;

import be_viemp3.viemp3.common.service.EntityQueryService;
import be_viemp3.viemp3.dto.request.auth.RegisterRequest;
import be_viemp3.viemp3.dto.request.auth.UpdateProfileRequest;
import be_viemp3.viemp3.dto.request.auth.UpdateUserRoleRequest;
import be_viemp3.viemp3.dto.response.auth.UserResponse;
import be_viemp3.viemp3.entity.Role;
import be_viemp3.viemp3.entity.User;
import be_viemp3.viemp3.enums.RoleEnum;
import be_viemp3.viemp3.common.exception.EmailAlreadyExistsException;
import be_viemp3.viemp3.mapper.auth.UserMapper;
import be_viemp3.viemp3.repository.auth.UserRepository;
import be_viemp3.viemp3.service.file.FileStorageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final FileStorageService fileStorageService;
    private final EntityQueryService entityQueryService;
    private final PasswordEncoder passwordEncoder;
    private final SecurityService securityUtils;

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

    // ===== UPDATE ROLE FOR USER =====
    @Transactional
    public void updateUserRoles(UpdateUserRoleRequest request) {
        User user = entityQueryService.findUserById(request.getUserId());

        // Role hiện tại của user
        Set<RoleEnum> currentRoles = user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        // Lấy role được chọn (ưu tiên theo cấp)
        Set<RoleEnum> requestRoles = request.getRoles();

        RoleEnum selectedRole;
        if (requestRoles.contains(RoleEnum.ADMIN)) {
            selectedRole = RoleEnum.ADMIN;
        } else if (requestRoles.contains(RoleEnum.PREMIUM)) {
            selectedRole = RoleEnum.PREMIUM;
        } else if (requestRoles.contains(RoleEnum.MOD)) {
            selectedRole = RoleEnum.MOD;
        } else {
            selectedRole = RoleEnum.USER;
        }

        // Build role mới
        Set<RoleEnum> newRoles = new HashSet<>();

        switch (selectedRole) {
            case USER:
                newRoles.add(RoleEnum.USER);
                break;

            case MOD:
                newRoles.add(RoleEnum.USER);
                newRoles.add(RoleEnum.MOD);
                break;

            case PREMIUM:
                newRoles.add(RoleEnum.USER);

                // CHỈ giữ MOD nếu user đã có từ trước
                if (currentRoles.contains(RoleEnum.MOD)) {
                    newRoles.add(RoleEnum.MOD);
                }

                newRoles.add(RoleEnum.PREMIUM);
                break;

            case ADMIN:
                newRoles.add(RoleEnum.ADMIN);
                break;
        }

        // Convert sang entity Role
        Set<Role> roles = roleService.getRolesFromEnums(newRoles);

        user.setRoles(roles);
        userRepository.save(user);
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
    public User buildUser(String username, String email, String password){
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(false);
        Role roleUser = entityQueryService.findRoleByName(RoleEnum.USER);
        user.getRoles().add(roleUser);
        return user;
    }

    @Transactional
    public User processOAuthPostLogin(String username, String email, String avatar) {
        return userRepository.findByEmail(email).orElseGet(() -> {
            // Chỉ chạy đoạn này nếu không tìm thấy email trong DB
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setUsername(username);
            newUser.setAvatar(avatar);
            newUser.setPassword(""); // OAuth2 không cần password
            newUser.setEnabled(true); // Tin tưởng xác thực từ Google

            // Gán Role mặc định
            Role roleUser = entityQueryService.findRoleByName(RoleEnum.USER);
            newUser.getRoles().add(roleUser);

            return userRepository.save(newUser);
        });
    }
}

