package dev.teknowledge.service.authServiceImpl;

import dev.teknowledge.DTO.request.LoginRequestDTO;
import dev.teknowledge.DTO.request.UserDTO;
import dev.teknowledge.DTO.response.AuthResponse;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthUserDetails {
    AuthResponse createUserHandler(UserDTO userDTO) throws Exception;

    AuthResponse signIn(@RequestBody LoginRequestDTO loginRequest);
}

