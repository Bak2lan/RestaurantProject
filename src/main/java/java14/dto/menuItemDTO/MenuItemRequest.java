package java14.dto.menuItemDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemRequest {
    private String name;
    private String image;
    private int price;
    private String description;
    private boolean isVegetarian;
}
