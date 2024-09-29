package java14.service;

import java14.dto.authenticationDTO.AuthenticationResponse;
import java14.dto.authenticationDTO.ProfileResponse;
import java14.dto.authenticationDTO.SignInRequest;
import java14.dto.authenticationDTO.SignUpRequest;

public interface AuthenticationService {
    AuthenticationResponse signUp(SignUpRequest signUpRequest);
    AuthenticationResponse signIn(SignInRequest signInRequest);
    ProfileResponse getProfile();
}
