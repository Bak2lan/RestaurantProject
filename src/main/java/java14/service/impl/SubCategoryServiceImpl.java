package java14.service.impl;
import java14.dto.messageDTO.SimpleResponse;
import java14.dto.subCategoryDTO.SubCategoryRequest;
import java14.dto.subCategoryDTO.SubCategoryResponse;
import java14.entity.Category;
import java14.entity.SubCategory;
import java14.repository.CategoryRepository;
import java14.repository.SubCategoryRepository;
import java14.service.SubCategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;



@Service
@Transactional
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public SimpleResponse saveToCategory(Long categoryId, SubCategoryRequest subCategoryRequest) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new NoSuchElementException(String.format("Not found category with id %s", categoryId)));
        SubCategory subCategory= new SubCategory();
        subCategory.setName(subCategoryRequest.getName());
        subCategory.setCategory(category);
        System.out.println(subCategory.getName());
        subCategoryRepository.save(subCategory);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully saved to Category").build();
    }

    @Override
    public SubCategoryResponse getById(Long id) {
      return  subCategoryRepository.getByIdSubCategory(id);

    }

    @Override
    public List<SubCategoryResponse> getAllSubCategories() {
        return subCategoryRepository.getAllSubCategories();
    }

    @Override
    public SimpleResponse delete(Long id) {
        SubCategory subCategory = subCategoryRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("Not found SubCategory with id %s", id)));
        subCategoryRepository.deleteById(subCategory.getId());
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("SubCategory with id "+id +" is successfully deleted")
                .build();
    }

    @Override
    public SimpleResponse update(Long id, SubCategoryRequest subCategoryRequest) {
        SubCategory subCategory = subCategoryRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("Not found SubCategory with id %s", id)));
        subCategory.setName(subCategoryRequest.getName());
        subCategoryRepository.save(subCategory);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("SubCategory with id "+ id +" is updated")
                .build();
    }

    @Override
    public List<SubCategoryResponse> getAllSubCategoriesByCategoryId(Long categoryId) {
        return subCategoryRepository.getAllSubCategoriesSort(categoryId);
    }

    @Override
    public Map<String, List<SubCategoryResponse>> getAllSubCategoryGroupingByCategory() {
        List<SubCategoryResponse> allSubCategoryGroupByCategories = subCategoryRepository.getAllSubCategoryGroupByCategories();
        return allSubCategoryGroupByCategories.stream()
                .collect(Collectors.groupingBy(SubCategoryResponse::getCategoryName));

    }


}
