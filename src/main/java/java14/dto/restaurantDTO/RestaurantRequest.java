package java14.dto.restaurantDTO;

import java14.enums.RestType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RestaurantRequest {
    private String name;
    private String location;
    private RestType restType;
}