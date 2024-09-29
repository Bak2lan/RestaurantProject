package java14.dto.chequeDTO;

import java14.entity.MenuItem;
import java14.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChequeRequest {
    private double totalSumWithService;
    private int priceAverage;
    private LocalDate createdAt;
    private User user;
    private List<MenuItem>menuItems;
}
