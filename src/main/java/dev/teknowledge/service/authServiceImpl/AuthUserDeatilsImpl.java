package dev.teknowledge.service.authServiceImpl;

import dev.teknowledge.DTO.request.LoginRequestDTO;
import dev.teknowledge.DTO.request.UserDTO;
import dev.teknowledge.DTO.response.AuthResponse;
import dev.teknowledge.enums.ROLE;
import dev.teknowledge.exception.UserAlreadyExistException;
import dev.teknowledge.model.User;
import dev.teknowledge.repository.UserRepository;
import dev.teknowledge.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthUserDeatilsImpl implements AuthUserDetails{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsImpl customUserDetails;



    @Override
    public AuthResponse createUserHandler(UserDTO userDTO) throws Exception {

        Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());

        if (existingUser.isPresent()) {
            throw new UserAlreadyExistException("Email already exists with another account");
        }

        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());


        user.setEmail(user.getEmail());
        user.setName(user.getName());
        user.setRole(user.getRole());
        user.getCreateDate();
        user.getUpdateDate();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setTitle("Welcome " + user.getEmail());
        authResponse.setMessage("Register success");
        authResponse.setRole(savedUser.getRole());

        return authResponse;
    }

    @Override
    public AuthResponse signIn(LoginRequestDTO loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username, password);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty() ? String.valueOf(ROLE.USER) : authorities.iterator().next().getAuthority();

        String jwt = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage("Login success");
        authResponse.setTitle(jwt);
        authResponse.setRole(ROLE.valueOf(role));

        return authResponse;
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetails.loadUserByUsername(username);

        if(userDetails == null){
            throw new BadCredentialsException("Invalid username...");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password....");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
    }
}
