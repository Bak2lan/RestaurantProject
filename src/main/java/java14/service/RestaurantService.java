package java14.service;

import java14.dto.messageDTO.SimpleResponse;
import java14.dto.restaurantDTO.RestaurantRequest;
import java14.dto.restaurantDTO.RestaurantResponse;

import java.util.List;

public interface RestaurantService {

    SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest);
    RestaurantResponse getRestaurantById(Long id);
    List<RestaurantResponse>getAllRestaurants();
    SimpleResponse updateRestaurant(Long id, RestaurantRequest restaurantRequest);
    SimpleResponse deleteRestaurant(Long id);
}
