package dev.kiki.forum.auth;

import dev.kiki.forum.auth.jwt.JwtTokenService;
import dev.kiki.forum.user.User;
import dev.kiki.forum.user.UserService;
import dev.kiki.forum.user.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final JwtTokenService jwtTokenService;

    @PostMapping("/sign-up")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        User user = userService.createUser(registerRequest);
        UserInfoDto userInfoDto = new UserInfoDto(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getRole()
        );

        RegisterResponse response = new RegisterResponse(
                "Registered successfully",
                userInfoDto
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        User authenticatedUser = authService.authenticateUser(loginRequest);
        String accessToken = jwtTokenService.generateToken(authenticatedUser);
        Map<String, Object> user = Map.of(
                "id", authenticatedUser.getId(),
                "email", authenticatedUser.getEmail(),
                "username", authenticatedUser.getUsername(),
                "role", authenticatedUser.getRole().name()
        );

        LoginResponse loginResponse = new LoginResponse(
                "Login Successful",
                accessToken,
                user,
                jwtTokenService.expirationTime()
        );

        return ResponseEntity.ok(loginResponse);
    }

}
