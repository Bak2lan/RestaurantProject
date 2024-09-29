package java14.dto.authenticationDTO;

import java14.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
@NonNull
@Data
@Builder
public class ProfileResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Role role;
}
