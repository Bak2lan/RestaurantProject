package java14.dto.subCategoryDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SubCategoryResponse {
    private String name;
    private String categoryName;

    public SubCategoryResponse(String name) {
        this.name = name;
    }

    public SubCategoryResponse(String name, String categoryName) {
        this.name = name;
        this.categoryName = categoryName;
    }
}

