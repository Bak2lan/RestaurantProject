package java14.dto.stopListDTO;

import java14.dto.menuItemDTO.MenuItemStopListResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StopListResponse {
    private MenuItemStopListResponse food;
    private String reason;
    private LocalDate date;
}
