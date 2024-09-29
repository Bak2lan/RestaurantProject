package java14.service.impl;

import java14.dto.categoryDTO.CategoryRequest;
import java14.dto.categoryDTO.CategoryResponse;
import java14.dto.messageDTO.SimpleResponse;
import java14.entity.Category;
import java14.repository.CategoryRepository;
import java14.service.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    public SimpleResponse saveCategory(CategoryRequest categoryRequest) {
        Category category= new Category();
        category.setName(categoryRequest.getName());
        categoryRepository.save(category);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("Category successfully saved")
                .build();
    }

    @Override
    public CategoryResponse getById(Long id) {
        return categoryRepository.getCategoryByid(id);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.getAllCategory();
    }

    @Override
    public SimpleResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("Not found category with id %s", id)));
        category.setName(categoryRequest.getName());
        categoryRepository.save(category);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully updated").build();
    }

    @Override
    public SimpleResponse delete(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("Not found category with id %s", id)));
        categoryRepository.deleteById(category.getId());
         return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully deleted").build();

    }
}
