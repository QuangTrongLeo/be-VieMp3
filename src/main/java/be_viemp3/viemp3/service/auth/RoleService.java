package be_viemp3.viemp3.service.auth;

import be_viemp3.viemp3.dto.response.auth.RoleResponse;
import be_viemp3.viemp3.mapper.auth.RoleMapper;
import be_viemp3.viemp3.repository.auth.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<RoleResponse> getAllRoles() {
        return RoleMapper.toResponseList(roleRepository.findAll());
    }
}
