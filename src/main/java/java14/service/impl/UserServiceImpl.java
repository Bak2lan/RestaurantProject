package java14.service.impl;
import java14.configSecurity.jwt.JWTService;
import java14.dto.authenticationDTO.ProfileResponse;
import java14.dto.messageDTO.SimpleResponse;
import java14.dto.userDTO.UserRequest;
import java14.dto.userDTO.UserResponse;
import java14.entity.Restaurant;
import java14.entity.User;
import java14.enums.Role;
import java14.exception.MyException;
import java14.repository.RestaurantRepository;
import java14.repository.UserRepository;
import java14.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.NoSuchElementException;


@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl  implements UserService {
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    @PostConstruct
    public void saveAdmin() {
        if (!userRepository.existsUserByFirstName("Admin")) {
            assignAdminToRestaurant();
        }else {
            System.out.println("Already saved admin");
        }
    }

    @Override
    public void assignAdminToRestaurant() {
        User user= new User();
        user.setFirstName("Admin");
        user.setLastName("Admin");
        user.setEmail("admin@mail.ru");
        user.setPassword(passwordEncoder.encode("admin1234"));
        user.setExperience(5);
        user.setPhoneNumber("0558898754");
        user.setRole(Role.ADMIN);
        user.setDateOfBirth(LocalDate.of(1999,4,1));
        userRepository.save(user);
    }

    @Override
    public SimpleResponse saveUser(UserRequest userRequest){
        User user= new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setRole(userRequest.getRole());
        user.setDateOfBirth(userRequest.getDateOfBirth());
        user.setExperience(userRequest.getExperience());
        userRepository.save(user);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("User with id "+user.getId() +" is saved")
                .build();
    }

    @Override
    public UserResponse getByIdUser(Long id) {
        return userRepository.getByIdUser(id);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public SimpleResponse updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("User with id %s not found", id)));
        ProfileResponse profile = jwtService.getProfile();
        if (!user.getEmail().equals(profile.getEmail()) &&!profile.getRole().equals(Role.ADMIN)){
            return SimpleResponse.
                    builder()
                    .httpStatus(HttpStatus.FORBIDDEN)
                    .message("This user can be changed only by user with email "+ user.getEmail()+" or by Admin")
                    .build();
        }
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword()  );
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setRole(userRequest.getRole());
        user.setDateOfBirth(userRequest.getDateOfBirth());
        user.setExperience(userRequest.getExperience());
        userRepository.save(user);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("User with id "+user.getId() +" is updated")
                .build();
    }

    @Override
    public SimpleResponse deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("User with id %s not found", id)));
        userRepository.deleteById(user.getId());
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("User with id "+user.getId() +" is deleted")
                .build();
    }

    @Override
    public SimpleResponse assignUserToRestaurant(Long restaurantId, Long userId) {

        try {
            Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() ->
                    new NoSuchElementException(String.format("Restaurant with id %s not found", restaurantId)));
            User user = userRepository.findById(userId).orElseThrow(() ->
                    new NoSuchElementException(String.format("User with id %s not found", userId)));
            for (User user1:restaurant.getUsers()){
                if (user.getEmail().equals(user1.getEmail())){
                    userRepository.deleteById(userId);
                    throw new MyException("Email like this is already exist in this restaurant");
                }
            }
            LocalDate currentDate=LocalDate.now();
            LocalDate birthDate=user.getDateOfBirth();
            Period period=Period.between(birthDate,currentDate);
            int age = period.getYears();
            if (user.getRole().equals(Role.CHEF)){
                if (age<25||age>45){
                    userRepository.deleteById(userId);
                    throw new MyException("Chef age must be between 25 and 45");
                }
                if (user.getExperience()<2){
                    userRepository.deleteById(userId);
                    throw new MyException("Chef experience must be more 2 years");
                }
                if (!user.getPhoneNumber().startsWith("+996")&&user.getPhoneNumber().length()!=13){
                    userRepository.deleteById(userId);
                    throw new MyException("Phone number must be star with +996 and equals 13 symbol");
                }
                if (user.getPassword().length()<4){
                    userRepository.deleteById(userId);
                    throw new MyException("Password must be more 4 symbol");
                }
            }
            if (user.getRole().equals(Role.WAITER)){
                if (age<18||age>30){
                    userRepository.deleteById(userId);
                    throw new MyException("Waiter age must be between 18 and 30");
                }
                if (user.getExperience()<1){
                    userRepository.deleteById(userId);
                    throw new MyException("Waiter experience must be more 1 years");
                }
                if (!user.getPhoneNumber().startsWith("+996")&&user.getPhoneNumber().length()!=13){
                    userRepository.deleteById(userId);
                    throw new MyException("Phone number must be star with +996 and equals 13 symbol");
                }
                if (user.getPassword().length()<4){
                    userRepository.deleteById(userId);
                    throw new MyException("Password must be more 4 symbol");
                }
            }
            if (restaurant.getUsers().size()<15) {
                restaurant.getUsers().add(user);
                restaurant.setNumberOfEmployees(restaurant.getUsers().size());
                user.setRestaurant(restaurant);
                userRepository.save(user);
                restaurantRepository.save(restaurant);
                return SimpleResponse
                        .builder()
                        .httpStatus(HttpStatus.OK)
                        .message("User is successfully assigned to Restaurant"+" , Count of Employees: " + restaurant.getUsers().size())
                        .build();
            }else return SimpleResponse
                    .builder()
                    .message(" There is no vacancy, " + "Count of Employees: " + restaurant.getUsers().size())
                    .build();




        }catch (MyException e){
            System.out.println(e.getMessage());
        }return null;
    }
}
