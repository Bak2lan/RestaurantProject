package java14.service.impl;

import java14.configSecurity.jwt.JWTService;
import java14.dto.authenticationDTO.AuthenticationResponse;
import java14.dto.authenticationDTO.ProfileResponse;
import java14.dto.authenticationDTO.SignInRequest;
import java14.dto.authenticationDTO.SignUpRequest;
import java14.entity.User;
import java14.repository.UserRepository;
import java14.service.AuthenticationService;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponse signUp(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())){
            throw new EntityExistsException(String.format("User with email %s is already exist",signUpRequest.getEmail()));
        }
            User user= new User();
            user.setFirstName(signUpRequest.getFirstName());
            user.setLastName(signUpRequest.getLastName());
            user.setDateOfBirth(signUpRequest.getDateOfBirth());
            user.setExperience(signUpRequest.getExperience());
            user.setEmail(signUpRequest.getEmail());
            user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
            user.setPhoneNumber(signUpRequest.getPhoneNumber());
            user.setRole(signUpRequest.getRole());
            userRepository.save(user);
            String token = jwtService.generateToken(user);

        return AuthenticationResponse
                .builder()
                .email(user.getEmail())
                .token(token)
                .role(user.getRole())
                .build();
    }

    @Override
    public AuthenticationResponse signIn(SignInRequest signInRequest) {
        User user = userRepository.getUserByEmail(signInRequest.getEmail()).orElseThrow(() -> new NoSuchElementException(String.format("Not found User with email %s ", signInRequest.getEmail())));

        if (!userRepository.existsByEmail(signInRequest.getEmail())){
            throw new NoSuchElementException("User with this email not found");
        }

        if (signInRequest.getEmail().isBlank()){
            throw new NoSuchElementException("Not found email");
        }
        if (!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())){
            throw new NoSuchElementException("Invalid password");
        }
        String token = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @Override
    public ProfileResponse getProfile() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String email = authentication.getName();
        User user = userRepository.getUserByEmail(email).orElseThrow(() -> new NoSuchElementException(String.format("Not found user with email %s", email)));
        return ProfileResponse
                .builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
