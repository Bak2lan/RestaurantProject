package java14.dto.menuItemDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GlobalSearchResponse {
    private String foodName;
    private String subCategoryName;
    private String CategoryName;
}
