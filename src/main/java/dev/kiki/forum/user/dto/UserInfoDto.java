package dev.kiki.forum.user.dto;

import dev.kiki.forum.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserInfoDto {
    private UUID id;
    private String email;
    private String username;
    private Role role;
}
