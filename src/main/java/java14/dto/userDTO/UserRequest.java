package java14.dto.userDTO;

import jakarta.validation.constraints.Email;
import java14.enums.Role;
import java14.validation.PasswordValidation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
public class UserRequest {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    @Email
    private String email;
    @PasswordValidation
    private String password;
    private String phoneNumber;
    private Role role;
    private int experience;
}
