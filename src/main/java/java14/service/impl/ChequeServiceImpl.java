package java14.service.impl;

import java14.configSecurity.jwt.JWTService;
import java14.dto.authenticationDTO.ProfileResponse;

import java14.dto.chequeDTO.*;
import java14.dto.messageDTO.SimpleResponse;
import java14.dto.userDTO.UserResponseForCheque;
import java14.entity.Cheque;
import java14.entity.MenuItem;
import java14.entity.Restaurant;
import java14.entity.User;
import java14.enums.Role;
import java14.repository.ChequeRepository;
import java14.repository.MenuItemRepository;
import java14.repository.RestaurantRepository;
import java14.repository.UserRepository;
import java14.service.ChequeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;



@Service
@Transactional
@RequiredArgsConstructor
public class ChequeServiceImpl implements ChequeService {
    private final MenuItemRepository menuItemRepository;
    private final UserRepository userRepository;
    private final ChequeRepository chequeRepository;
    private final RestaurantRepository restaurantRepository;
    private final JWTService jwtService;


    @Override
    public SimpleResponse saveCheque(List<Long>menuItems, Long userId) {
        List<MenuItem> allById = menuItemRepository.findAllById(menuItems);
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException(String.format("Not found user with id %s", userId)));
        if (user.getRole() != Role.WAITER){
            return SimpleResponse
                    .builder()
                    .httpStatus(HttpStatus.FORBIDDEN)
                    .message("Only Waiter can create new cheque ")
                    .build();
        }
        int totalPriceFood=0;

        for (MenuItem menuItem:allById){
            totalPriceFood+=menuItem.getPrice();

        }
        int average=(totalPriceFood/allById.size());
        Restaurant restaurant = user.getRestaurant();
        double service = restaurant.getService();
        double serviceUser=(totalPriceFood*service)/100;
        double totalSumWithService=(serviceUser+totalPriceFood);



        ChequeRequest chequeRequest= new ChequeRequest();
        chequeRequest.setMenuItems(allById);
        chequeRequest.setUser(user);
        chequeRequest.setPriceAverage(average);
        chequeRequest.setTotalSumWithService(totalSumWithService);


        Cheque cheque= new Cheque();
        cheque.setCreatedAt(LocalDate.now());
        cheque.setMenuItems(chequeRequest.getMenuItems());
        cheque.setUser(chequeRequest.getUser());
        cheque.setPriceAverage(chequeRequest.getPriceAverage());
        chequeRepository.save(cheque);


        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("Cheque successfully saved")
                .build();
    }

    @Override
    public ChequeResponse getByIdCheque(Long id) {
        Cheque cheque = chequeRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("Not found cheque with id %s", id)));
        User user = cheque.getUser();
        Restaurant restaurant = user.getRestaurant();
        int priceAverage = cheque.getPriceAverage();
        double service = restaurant.getService();
        double getPercent=(priceAverage*service)/100;
        double totalSumWithService=(priceAverage+getPercent);

        UserResponseForCheque userResponseForCheque= new UserResponseForCheque();
        userResponseForCheque.setFirstName(user.getFirstName());
        userResponseForCheque.setLastName(user.getLastName());


        ChequeResponse chequeResponse = new ChequeResponse();
        chequeResponse.setUserResponse(userResponseForCheque);
        chequeResponse.setMenuItems(cheque.getMenuItems());
        chequeResponse.setService(restaurant.getService());
        chequeResponse.setCreatedAt(LocalDate.now());
        chequeResponse.setPriceAverage(cheque.getPriceAverage());
        chequeResponse.setTotalAmount(totalSumWithService);
        return chequeResponse;
    }

    @Override
    public List<ChequeResponse> getAllCheques() {
        List<Cheque> all = chequeRepository.findAll();
        List<ChequeResponse>chequeResponses= new ArrayList<>();
        for (Cheque cheque:all){
            UserResponseForCheque userResponseForCheque= new UserResponseForCheque(
                    cheque.getUser().getFirstName(),
                    cheque.getUser().getLastName()
            );
            double totalSumWithService=getTotal(cheque);
            ChequeResponse chequeResponse= new ChequeResponse(
                    totalSumWithService,
                    cheque.getPriceAverage(),
                    cheque.getUser().getRestaurant().getService(),
                    cheque.getCreatedAt(),
                    userResponseForCheque,
                    cheque.getMenuItems()
            );
            chequeResponses.add(chequeResponse);

        }


        return chequeResponses;
    }
    double getTotal(Cheque cheque){
        double service=cheque.getUser().getRestaurant().getService();
        double percent=cheque.getPriceAverage()*service/100;
       return percent+cheque.getPriceAverage();


    }

    @Override
    public SimpleResponse updateCheque(Long id, ChequeRequestForUpdate chequeRequest) {
        ProfileResponse profile = jwtService.getProfile();
        User user = userRepository.getUserByChequeId(id).orElseThrow(() -> new NoSuchElementException("Not found waiter with this cheque id"));
        Cheque cheque = chequeRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("Not found Cheque with id %s", id)));
        if (!user.getEmail().equals(profile.getEmail()) && !profile.getRole().equals(Role.ADMIN)){
            return SimpleResponse
                    .builder()
                    .httpStatus(HttpStatus.FORBIDDEN)
                    .message("This cheque only can be updated by user with email :"+ user.getEmail()+" or by Admin")
                    .build();
        }
        cheque.setPriceAverage(chequeRequest.getPriceAverage());
        cheque.setCreatedAt(chequeRequest.getCreatedAt());
        System.out.println("SAVED");
        chequeRepository.save(cheque);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("Cheque with id "+id+" updated")
                .build();
    }

    @Override
    public SimpleResponse deleteCheque(Long id) {
        Cheque cheque = chequeRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("Not found cheque with id %s", id)));
        cheque.setUser(null);
        cheque.setMenuItems(null);
        chequeRepository.deleteById(cheque.getId());
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("Cheque with id "+id+" deleted")
                .build();
    }

    @Override
    public TotalAmountResponse getAmountForDay(Long waiterId, LocalDate fromDay) {
        User user = userRepository.findById(waiterId).orElseThrow(() -> new NoSuchElementException(String.format("Not found user with id %s ", waiterId)));
        TotalAmountResponse totalAmountResponse= new TotalAmountResponse();
        double totalSum=0;
        for (Cheque cheque: user.getCheques()){
            if (cheque.getCreatedAt().isEqual(fromDay)){
                totalSum+=getTotal(cheque);
            }
        }
        totalAmountResponse.setTotalAmount(totalSum);
        totalAmountResponse.setDate(fromDay);

        return totalAmountResponse;
    }

    @Override
    public AverageAmountRestaurantResponse getAverageAmount(Long restaurantId, LocalDate day) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new NoSuchElementException(String.format("Not found Restaurant with id %s", restaurantId)));
        List<User> users = restaurant.getUsers();
        AverageAmountRestaurantResponse averageAmountRestaurantResponse= new AverageAmountRestaurantResponse();
        double totalAmount=0;
        double countOfCheque=0;

        for (User user:users){
            for (Cheque cheque:user.getCheques()){
                if (cheque.getCreatedAt().isEqual(day)){
                    totalAmount+=getTotal(cheque);
                    countOfCheque++;
                }
            }


        }
       double averageAmount=(totalAmount/countOfCheque);
        averageAmountRestaurantResponse.setRestaurantName(restaurant.getName());
        averageAmountRestaurantResponse.setAverageAmount(averageAmount);
        averageAmountRestaurantResponse.setLocalDate(day);
        return averageAmountRestaurantResponse;
    }


}
