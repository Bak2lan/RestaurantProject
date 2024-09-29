package java14.service;

import java14.dto.chequeDTO.AverageAmountRestaurantResponse;
import java14.dto.chequeDTO.ChequeRequestForUpdate;
import java14.dto.chequeDTO.ChequeResponse;
import java14.dto.chequeDTO.TotalAmountResponse;
import java14.dto.messageDTO.SimpleResponse;

import java.time.LocalDate;
import java.util.List;

public interface ChequeService {

    SimpleResponse saveCheque(List<Long>menuItems, Long userId);
    ChequeResponse getByIdCheque(Long id);
    List<ChequeResponse>getAllCheques();
    SimpleResponse updateCheque(Long id, ChequeRequestForUpdate chequeRequest);
    SimpleResponse deleteCheque(Long id);
    TotalAmountResponse getAmountForDay(Long waiterId, LocalDate fromDay);
    AverageAmountRestaurantResponse getAverageAmount(Long restaurantId,LocalDate day);

}
