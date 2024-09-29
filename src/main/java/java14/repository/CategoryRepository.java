package java14.repository;

import java14.dto.categoryDTO.CategoryResponse;
import java14.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query("select new java14.dto.categoryDTO.CategoryResponse(c.name) from Category c where c.id=:id")
    CategoryResponse getCategoryByid(Long id);

    @Query("select new java14.dto.categoryDTO.CategoryResponse(c.name) from Category c")
    List<CategoryResponse> getAllCategory();
}
