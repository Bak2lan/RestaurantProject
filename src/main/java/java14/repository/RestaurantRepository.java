package java14.repository;

import java14.dto.restaurantDTO.RestaurantResponse;
import java14.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {
    @Query("select new java14.dto.restaurantDTO.RestaurantResponse(r.id,r.name,r.location,r.restType,r.numberOfEmployees,r.service) from Restaurant r where r.id=:id ")
    RestaurantResponse getByIdRest(Long id);
    @Query("select new java14.dto.restaurantDTO.RestaurantResponse(r.id,r.name,r.location,r.restType,r.numberOfEmployees,r.service) from Restaurant r")
    List<RestaurantResponse>getAllRestaurantt();
}
