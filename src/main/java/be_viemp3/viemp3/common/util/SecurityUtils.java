package be_viemp3.viemp3.common.util;

import be_viemp3.viemp3.entity.User;
import be_viemp3.viemp3.repository.auth.UserRepository;
import be_viemp3.viemp3.service.auth.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtils {

    private final UserRepository userRepository;
    private final UserService userService;

    public User getCurrentUser() {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userService.findUserByEmail(email);
    }
}
