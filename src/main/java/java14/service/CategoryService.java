package java14.service;

import java14.dto.categoryDTO.CategoryRequest;
import java14.dto.categoryDTO.CategoryResponse;
import java14.dto.messageDTO.SimpleResponse;

import java.util.List;

public interface CategoryService {
    SimpleResponse saveCategory(CategoryRequest categoryRequest);
    CategoryResponse getById(Long id);
    List<CategoryResponse>getAllCategories();
    SimpleResponse updateCategory(Long id,CategoryRequest categoryRequest);
    SimpleResponse delete(Long id);
}
