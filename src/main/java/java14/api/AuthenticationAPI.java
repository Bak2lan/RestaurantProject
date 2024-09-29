package java14.api;

import java14.dto.authenticationDTO.AuthenticationResponse;
import java14.dto.authenticationDTO.ProfileResponse;
import java14.dto.authenticationDTO.SignInRequest;
import java14.dto.authenticationDTO.SignUpRequest;
import java14.service.AuthenticationService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationAPI {
    private final AuthenticationService authenticationService;

    @PostMapping("/signUp")
    public AuthenticationResponse signUp(@RequestBody SignUpRequest signUpRequest){
        return authenticationService.signUp(signUpRequest);
    }

    @PostMapping("/signIn")
    public AuthenticationResponse signIn(@RequestBody SignInRequest signInRequest){
        return authenticationService.signIn(signInRequest);
    }


    @PermitAll
    @GetMapping()
    public ProfileResponse getProfile(){
        return authenticationService.getProfile();
    }
}
