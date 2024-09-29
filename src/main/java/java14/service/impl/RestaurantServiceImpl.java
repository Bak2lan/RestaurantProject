package java14.service.impl;

import java14.dto.messageDTO.SimpleResponse;
import java14.dto.restaurantDTO.RestaurantRequest;
import java14.dto.restaurantDTO.RestaurantResponse;
import java14.entity.Restaurant;
import java14.repository.RestaurantRepository;
import java14.service.RestaurantService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository repository;
    @Override
    public SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest) {
        Restaurant restaurant= new Restaurant();
        restaurant.setName(restaurantRequest.getName());
        restaurant.setLocation(restaurantRequest.getLocation());
        restaurant.setRestType(restaurantRequest.getRestType());
         repository.save(restaurant);
        return SimpleResponse
                .builder().
                httpStatus(HttpStatus.OK)
                .message("Successfully saved")
                .build();
    }

    @Override
    public RestaurantResponse getRestaurantById(Long id) {
        Restaurant restaurant = repository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("Not found restaurant with id,%s", id)));
        RestaurantResponse rest = repository.getByIdRest(id);
        rest.setService(restaurant.getService());
        rest.setNumberOfEmployees(restaurant.getUsers().size());
        return  rest;

    }

    @Override
    public List<RestaurantResponse> getAllRestaurants() {
        return repository.getAllRestaurantt();
    }

    @Override
    public SimpleResponse updateRestaurant(Long id, RestaurantRequest restaurantRequest) {
        Restaurant restaurant = repository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("Not found Restaurant with id" + id)));
        restaurant.setName(restaurantRequest.getName());
        restaurant.setLocation(restaurantRequest.getLocation());
        restaurant.setRestType(restaurantRequest.getRestType());
         repository.save(restaurant);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("Restaurant with id "+id+" is successfully updated")
                .build();
    }

    @Override
    public SimpleResponse deleteRestaurant(Long id) {
        Restaurant restaurant = repository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("Not found Restaurant with id" + id)));
        repository.deleteById(restaurant.getId());
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("Restaurant with id "+id+" is successfully deleted")
                .build();
    }
}
