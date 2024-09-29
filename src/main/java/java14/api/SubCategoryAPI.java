package java14.api;

import java14.dto.messageDTO.SimpleResponse;
import java14.dto.subCategoryDTO.SubCategoryRequest;
import java14.dto.subCategoryDTO.SubCategoryResponse;
import java14.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/subCategories")
@RequiredArgsConstructor
public class SubCategoryAPI{
    private final SubCategoryService subCategoryService;

    @PostMapping("/{categoryId}")
    public SimpleResponse saveToCategory(@PathVariable Long categoryId,@RequestBody SubCategoryRequest subCategoryRequest){
        return subCategoryService.saveToCategory(categoryId,subCategoryRequest);

    }



    @GetMapping("/{id}")
    public SubCategoryResponse getById(@PathVariable Long id){
        return subCategoryService.getById(id);
    }

    @GetMapping
    public List<SubCategoryResponse> getAll(){
        return subCategoryService.getAllSubCategories();
    }

    @PutMapping("/{id}")
    public SimpleResponse update(@PathVariable Long id,
                                 @RequestBody SubCategoryRequest subCategoryRequest){
        return subCategoryService.update(id,subCategoryRequest);
    }

    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id){
        return subCategoryService.delete(id);
    }

    @GetMapping("/{categoryId}")
    public List<SubCategoryResponse> allSubCategoryByCategoryIdWithSort(@PathVariable Long categoryId){
        return subCategoryService.getAllSubCategoriesByCategoryId(categoryId);
    }
    @GetMapping("/groupingBy")
    public Map<String,List<SubCategoryResponse>>getAllSubCategoryGroupingByCategory(){
        return subCategoryService.getAllSubCategoryGroupingByCategory();
    }


}
