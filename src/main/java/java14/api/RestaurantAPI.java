package java14.api;

import java14.dto.messageDTO.SimpleResponse;
import java14.dto.restaurantDTO.RestaurantRequest;
import java14.dto.restaurantDTO.RestaurantResponse;
import java14.service.RestaurantService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantAPI {
    private final RestaurantService restaurantService;

    @PermitAll
    @PostMapping()
    public SimpleResponse saveRestaurant(@RequestBody RestaurantRequest restaurantRequest){
        return restaurantService.saveRestaurant(restaurantRequest);
    }

    @PermitAll
    @GetMapping
    public List<RestaurantResponse>getAllRestaurant(){
        return restaurantService.getAllRestaurants();
    }

    @PermitAll
    @GetMapping("/{id}")
    public RestaurantResponse getById(@PathVariable Long id){
        return restaurantService.getRestaurantById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public SimpleResponse update(@PathVariable Long id,
                                 @RequestBody RestaurantRequest restaurantRequest){
        return restaurantService.updateRestaurant(id,restaurantRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id){
        return restaurantService.deleteRestaurant(id);
    }
}
