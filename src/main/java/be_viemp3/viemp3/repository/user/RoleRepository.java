package be_viemp3.viemp3.repository.user;

import be_viemp3.viemp3.entity.Role;
import be_viemp3.viemp3.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum role);
    boolean existsByName(RoleEnum role);
}
