package java14.service;

import java14.dto.messageDTO.SimpleResponse;
import java14.dto.userDTO.UserRequest;
import java14.dto.userDTO.UserResponse;

import java.util.List;

public interface UserService {

    void assignAdminToRestaurant();

    SimpleResponse saveUser(UserRequest userRequest);
    UserResponse getByIdUser(Long id);
    List<UserResponse>getAllUsers();
    SimpleResponse updateUser(Long id, UserRequest userRequest);
    SimpleResponse deleteUser(Long id);
    SimpleResponse assignUserToRestaurant(Long restaurantId,Long userId);

}
