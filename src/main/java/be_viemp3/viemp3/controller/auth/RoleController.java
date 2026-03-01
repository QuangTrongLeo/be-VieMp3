package be_viemp3.viemp3.controller.auth;

import be_viemp3.viemp3.dto.response.auth.RoleResponse;
import be_viemp3.viemp3.service.auth.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.vie-mp3-url}/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<RoleResponse> getAllRoles() {
        return roleService.getAllRoles();
    }
}
