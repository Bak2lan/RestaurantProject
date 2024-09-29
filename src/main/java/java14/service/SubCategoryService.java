package java14.service;

import java14.dto.messageDTO.SimpleResponse;
import java14.dto.subCategoryDTO.SubCategoryRequest;
import java14.dto.subCategoryDTO.SubCategoryResponse;

import java.util.List;
import java.util.Map;

public interface SubCategoryService {

    SimpleResponse saveToCategory(Long categoryId, SubCategoryRequest subCategoryRequest);

    SubCategoryResponse getById(Long id);

    List<SubCategoryResponse> getAllSubCategories();

    SimpleResponse delete(Long id);

    SimpleResponse update(Long id, SubCategoryRequest subCategoryRequest);

    List<SubCategoryResponse> getAllSubCategoriesByCategoryId(Long categoryId);

   Map<String,List<SubCategoryResponse>>getAllSubCategoryGroupingByCategory();
}
