package java14.dto.chequeDTO;

import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TotalAmountResponse {
    private LocalDate date;
    private double totalAmount;
}
