package java14.dto.chequeDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChequeRequestForUpdate {
    private int priceAverage;
    private LocalDate createdAt;
}
