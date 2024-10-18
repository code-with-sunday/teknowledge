package dev.teknowledge.DTO.response;

import dev.teknowledge.enums.ROLE;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthResponse {
    private String Title;
    private String message;
    private ROLE role;
}