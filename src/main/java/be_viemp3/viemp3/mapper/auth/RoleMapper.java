package be_viemp3.viemp3.mapper.auth;

import be_viemp3.viemp3.dto.response.auth.RoleResponse;
import be_viemp3.viemp3.entity.Role;

import java.util.List;

public class RoleMapper {

    public static RoleResponse toResponse(Role role) {
        if (role == null) return null;

        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName().name())
                .build();
    }

    public static List<RoleResponse> toResponseList(List<Role> roles) {
        if (roles == null) return List.of();

        return roles.stream()
                .map(RoleMapper::toResponse)
                .toList();
    }
}
