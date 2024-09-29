package java14.dto.messageDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
@Getter
@Setter
@Builder
@AllArgsConstructor
public class SimpleResponse {
    private HttpStatus httpStatus;
    private String message;
}
