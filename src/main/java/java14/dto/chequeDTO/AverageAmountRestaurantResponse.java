package java14.dto.chequeDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AverageAmountRestaurantResponse {
    private String restaurantName;
    private double averageAmount;
    private LocalDate localDate;
}
