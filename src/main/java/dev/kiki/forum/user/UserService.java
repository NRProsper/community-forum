package dev.kiki.forum.user;

import dev.kiki.forum.exception.ResourceNotFoundException;
import dev.kiki.forum.user.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(RegisterRequest userDto) {
        User user = new User();

        user.setEmail(userDto.email());
        user.setUsername(userDto.username());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(userDto.password()));

        return userRepository.save(user);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!(auth instanceof AnonymousAuthenticationToken)) {
            String userName = auth.getName();
            return userRepository.findByEmail(userName).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        }else {
            throw new RuntimeException("User not found");
        }
    }

}
