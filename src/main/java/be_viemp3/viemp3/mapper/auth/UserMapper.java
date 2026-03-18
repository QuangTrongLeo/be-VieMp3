package be_viemp3.viemp3.mapper.auth;

import be_viemp3.viemp3.dto.response.auth.RoleResponse;
import be_viemp3.viemp3.dto.response.auth.UserResponse;
import be_viemp3.viemp3.entity.User;

import java.util.List;

public class UserMapper {

    public static UserResponse toResponse(User user) {
        if (user == null) return null;

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .roles(user.getRoles().stream()
                        .map(role -> RoleResponse.builder()
                                .id(role.getId())
                                .name(role.getName().name())
                                .build())
                        .toList())
                .build();
    }

    public static List<UserResponse> toResponseList(List<User> users) {
        if (users == null) return List.of();
        return users.stream()
                .map(UserMapper::toResponse)
                .toList();
    }
}
