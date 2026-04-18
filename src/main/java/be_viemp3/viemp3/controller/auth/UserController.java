package be_viemp3.viemp3.controller.auth;

import be_viemp3.viemp3.common.response.ApiResponse;
import be_viemp3.viemp3.dto.request.auth.UpdateProfileRequest;
import be_viemp3.viemp3.dto.request.auth.UpdateUserRoleRequest;
import be_viemp3.viemp3.dto.response.auth.UserResponse;
import be_viemp3.viemp3.service.auth.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.vie-mp3-url}/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // ===== GET MY PROFILE =====
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getMyProfile() {
        UserResponse response = userService.getMyProfile();
        return ResponseEntity.ok(
                ApiResponse.<UserResponse>builder()
                        .success(true)
                        .message("Lấy thông tin profile thành công")
                        .data(response)
                        .build()
        );
    }

    // ===== UPDATE PROFILE =====
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> updateProfile(@ModelAttribute UpdateProfileRequest request) {
        UserResponse response = userService.updateProfile(request);
        return ResponseEntity.ok(
                ApiResponse.<UserResponse>builder()
                        .success(true)
                        .message("Cập nhật profile thành công")
                        .data(response)
                        .build()
        );
    }

    // ===== GET ALL USERS =====
    @PreAuthorize("hasRole('MOD')")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        List<UserResponse> responses = userService.getAllUsers();
        return ResponseEntity.ok(
                ApiResponse.<List<UserResponse>>builder()
                        .success(true)
                        .message("Lấy danh sách user thành công")
                        .data(responses)
                        .build()
        );
    }

    // ===== UPDATE ROLE FOR USER =====
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/roles")
    public ResponseEntity<ApiResponse<Void>> updateUserRoles(@RequestBody UpdateUserRoleRequest request) {
        userService.updateUserRoles(request);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Cập nhật role cho user thành công")
                        .build()
        );
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/is-student")
    public ResponseEntity<ApiResponse<Boolean>> checkUserIsStudent() {
        boolean isStudent = userService.checkUserIsStudent();

        return ResponseEntity.ok(
                ApiResponse.<Boolean>builder()
                        .success(true)
                        .message(isStudent ? "Người dùng là sinh viên" : "Người dùng không phải là sinh viên")
                        .data(isStudent)
                        .build()
        );
    }
}
