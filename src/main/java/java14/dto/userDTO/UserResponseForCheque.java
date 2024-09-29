package java14.dto.userDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseForCheque {
    private String firstName;
    private String lastName;

    public UserResponseForCheque(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}

