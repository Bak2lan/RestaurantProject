package java14.dto.chequeDTO;

import java14.dto.userDTO.UserResponseForCheque;
import java14.entity.MenuItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class ChequeResponse {
    private double totalAmount;
    private int priceAverage;
    private double service;
    private LocalDate createdAt;
    private UserResponseForCheque userResponse;
    private List<MenuItem> menuItems;

    public ChequeResponse(double totalSumWithService, int priceAverage, double service, LocalDate createdAt, UserResponseForCheque userResponse, List<MenuItem> menuItems) {
        this.totalAmount = totalSumWithService;
        this.priceAverage = priceAverage;
        this.service = service;
        this.createdAt = createdAt;
        this.userResponse = userResponse;
        this.menuItems = menuItems;
    }

    public ChequeResponse(int priceAverage, LocalDate createdAt, UserResponseForCheque userResponse, List<MenuItem> menuItems) {
        this.priceAverage = priceAverage;
        this.createdAt = createdAt;
        this.userResponse = userResponse;
        this.menuItems = menuItems;
    }
}
