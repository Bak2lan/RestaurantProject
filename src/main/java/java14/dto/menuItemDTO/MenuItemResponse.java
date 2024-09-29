package java14.dto.menuItemDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemResponse {
    private String name;
    private String image;
    private int price;
    private String description;
    private boolean isVegetarian;

    public MenuItemResponse(String name, int price, String image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }
}
