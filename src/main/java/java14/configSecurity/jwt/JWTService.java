package java14.configSecurity.jwt;

import java14.dto.authenticationDTO.ProfileResponse;
import java14.entity.User;
import java14.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.NoSuchElementException;

@Component
public class JWTService {
    private final UserRepository userRepository;
    @Value("${jwt.secretKey}")
    private String secretKey;

    public JWTService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public String generateToken(User user){
       return JWT.create()
                .withClaim("email",user.getEmail())
                .withIssuedAt(new Date())
                .withExpiresAt(Date.from(ZonedDateTime.now().plusDays(3).toInstant()))
                .sign(Algorithm.HMAC256(secretKey));
    }

    public String verifyToken(String token){
        JWTVerifier jwtToken = JWT
                .require(Algorithm.HMAC256(secretKey))
                .build();
        DecodedJWT verify = jwtToken.verify(token);
        return verify.getClaim("email").asString();
    }

    public ProfileResponse getProfile(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String email = authentication.getName();
        User user = userRepository.getUserByEmail(email).orElseThrow(() -> new NoSuchElementException(String.format("User with email %s not found", email)));
        return ProfileResponse.
                builder()
                .firstName(user.getFirstName())
                .lastName(user.getUsername())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }


}
