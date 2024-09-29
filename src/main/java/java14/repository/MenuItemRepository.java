package java14.repository;

import java14.dto.menuItemDTO.GlobalSearchResponse;
import java14.dto.menuItemDTO.MenuItemResponse;
import java14.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem,Long> {
    @Query("select new java14.dto.menuItemDTO.MenuItemResponse(m.name,m.image,m.price,m.description,m.isVegetarian) from MenuItem m")
    List<MenuItemResponse>getAllMenuItem();

    @Query("select new java14.dto.menuItemDTO.MenuItemResponse(m.name,m.image,m.price,m.description,m.isVegetarian) from MenuItem m where m.id=:id")
    MenuItemResponse getByIdMenu(Long id);
    @Query("select new java14.dto.menuItemDTO.GlobalSearchResponse(m.name,ms.name,msc.name) from MenuItem m join m.subCategory ms join ms.category msc where m.name ilike  concat(:word,'%') or ms.name  ilike concat(:word,'%') or msc.name  ilike concat(:word,'%') ")
    List<GlobalSearchResponse> globalSearch(String word);
    @Query("select new java14.dto.menuItemDTO.MenuItemResponse(m.name,m.image,m.price,m.description,m.isVegetarian) from MenuItem m order by m.price asc ")
    List<MenuItemResponse>sortByPrice();

    @Query("select new java14.dto.menuItemDTO.MenuItemResponse(m.name,m.image,m.price,m.description,m.isVegetarian) from MenuItem m order by m.price desc ")
    List<MenuItemResponse>sortByPriceDesc();

    @Query("select new java14.dto.menuItemDTO.MenuItemResponse(m.name,m.image,m.price,m.description,m.isVegetarian) from MenuItem m where m.id in :menuItems" )
    List<MenuItemResponse>getAllMenuById(List<Long>menuItems);
}
