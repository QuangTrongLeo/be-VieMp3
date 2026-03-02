package be_viemp3.viemp3.dto.request.auth;

import be_viemp3.viemp3.enums.RoleEnum;
import lombok.Data;

import java.util.Set;

@Data
public class UpdateUserRoleRequest {
    private String userId;
    private Set<RoleEnum> roles;
}
