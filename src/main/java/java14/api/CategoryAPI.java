package java14.api;

import java14.dto.categoryDTO.CategoryRequest;
import java14.dto.categoryDTO.CategoryResponse;
import java14.dto.messageDTO.SimpleResponse;
import java14.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryAPI {
    private final CategoryService categoryService;

    @PostMapping
    public SimpleResponse save(@RequestBody CategoryRequest categoryRequest){
        return categoryService.saveCategory(categoryRequest);
    }
    @GetMapping
    public List<CategoryResponse>getAllCategory(){
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryResponse getById(@PathVariable Long id){
        return categoryService.getById(id);
    }

    @PutMapping("/{id}")
    public SimpleResponse update(@PathVariable Long id,
                                 @RequestBody CategoryRequest categoryRequest){
        return categoryService.updateCategory(id,categoryRequest);
    }

    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id){
        return categoryService.delete(id);




    }
}
