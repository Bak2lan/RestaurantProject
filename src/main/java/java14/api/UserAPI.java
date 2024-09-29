package java14.api;

import java14.dto.messageDTO.SimpleResponse;
import java14.dto.userDTO.UserRequest;
import java14.dto.userDTO.UserResponse;
import java14.service.UserService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserAPI {
    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public SimpleResponse save(@RequestBody UserRequest userRequest){
        return userService.saveUser(userRequest);
    }

    @PermitAll
    @GetMapping
    public List<UserResponse> getAllUser(){
        return userService.getAllUsers();
    }
    @PermitAll
    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id){
        return userService.getByIdUser(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public SimpleResponse deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    @PutMapping("/{id}")
    public SimpleResponse updateUser(@PathVariable Long id,
                                     @RequestBody UserRequest userRequest){
        return userService.updateUser(id,userRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{restId}/{userId}")
    public SimpleResponse assign (@PathVariable Long restId,
                                  @PathVariable Long userId){
        return userService.assignUserToRestaurant(restId,userId);
    }


}
