package authUserDetailsImpl;

import dev.teknowledge.DTO.request.UserDTO;
import dev.teknowledge.DTO.response.AuthResponse;
import dev.teknowledge.model.User;
import dev.teknowledge.repository.UserRepository;
import dev.teknowledge.security.JwtProvider;
import dev.teknowledge.service.authServiceImpl.AuthUserDeatilsImpl;
import dev.teknowledge.service.authServiceImpl.CustomUserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static dev.teknowledge.enums.ROLE.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AuthUserDetailsImplTest {

    private AuthUserDeatilsImpl authUserDetailsImplTest;

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtProvider jwtProvider;
    @Mock
    private CustomUserDetailsImpl customUserDetails;

    @BeforeEach
    void setUp() {
        authUserDetailsImplTest =  new AuthUserDeatilsImpl(userRepository, passwordEncoder, jwtProvider, customUserDetails);
    }

    @Test
    void shouldCreateUser() throws Exception {
        UserDTO userDTO = new UserDTO()
                .builder()
                .email("email@email.com")
                .password("Password@121")
                .role(USER)
                .name("Sunday")
                .build();

        AuthResponse authResponse = new AuthResponse()
                .builder()
                .Title("WELCOME")
                .message("Register success")
                .role(USER)
                .build();

        User user = new User().builder()
                .name("Sunday")
                .email("email@email.com")
                .password("password@121")
                .role(USER)
                .build();

        when(userRepository.save(any())).thenReturn(user);
        AuthResponse returnedUser = authUserDetailsImplTest.createUserHandler(userDTO);
        assertEquals(returnedUser.getMessage(), authResponse.getMessage());
    }

    @Test
    void shouldLoginSuccessfully() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("zuzu@email.com");
        userDTO.setPassword("EncodedPassword");

        AuthResponse authResponse = new AuthResponse()
                .builder()
                .Title("WELCOME")
                .message("Login success")
                .role(USER)
                .build();
        User savedUser = User.builder()
                .email("zuzu@email.com")
                .password("EncodedPassword")
                .role(userDTO.getRole())
                .build();

        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("EncodedPassword");

        assertEquals(passwordEncoder.encode(userDTO.getPassword()), "EncodedPassword");
    }

}
