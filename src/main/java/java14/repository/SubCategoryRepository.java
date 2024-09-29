package java14.repository;

import java14.dto.subCategoryDTO.SubCategoryResponse;
import java14.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory,Long> {
    @Query("select new java14.dto.subCategoryDTO.SubCategoryResponse(s.name) from SubCategory s where s.id=:id")
    SubCategoryResponse getByIdSubCategory(Long id);
    @Query("select new java14.dto.subCategoryDTO.SubCategoryResponse(s.name) from SubCategory s")
    List<SubCategoryResponse> getAllSubCategories();

    @Query("select new java14.dto.subCategoryDTO.SubCategoryResponse(s.name) from SubCategory s inner join s.category sc where sc.id=:categoryId order by s.name")
    List<SubCategoryResponse> getAllSubCategoriesSort(Long categoryId);
    @Query("select new java14.dto.subCategoryDTO.SubCategoryResponse(s.name,sc.name) from SubCategory s join s.category sc order by sc.name, s.name")
    List<SubCategoryResponse>getAllSubCategoryGroupByCategories();

}
