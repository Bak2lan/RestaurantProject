package java14.api;

import java14.dto.chequeDTO.ChequeResponse;
import java14.dto.messageDTO.SimpleResponse;
import java14.service.ChequeService;
import jakarta.annotation.security.PermitAll;
import java14.dto.chequeDTO.AverageAmountRestaurantResponse;
import java14.dto.chequeDTO.ChequeRequestForUpdate;
import java14.dto.chequeDTO.TotalAmountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/cheques")
@RequiredArgsConstructor
public class ChequeAPI {
    private final ChequeService chequeService;


    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    @PostMapping()
    public SimpleResponse saveCheque(@RequestParam List<Long>menuItems,
                                     @RequestParam Long userId){
     return    chequeService.saveCheque(menuItems,userId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    @GetMapping("/{id}")
    public ChequeResponse getByIdCheque(@PathVariable Long id){
        return chequeService.getByIdCheque(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    @GetMapping()
    public List<ChequeResponse>getAll(){
        return chequeService.getAllCheques();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    @PermitAll
    @PutMapping("/{id}")
    public SimpleResponse update(@PathVariable Long id,
                                 @RequestBody ChequeRequestForUpdate chequeRequest){
        return chequeService.updateCheque(id,chequeRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id){
        return chequeService.deleteCheque(id);
    }
    @PermitAll
    @GetMapping("/totalAmount")
    public TotalAmountResponse getTotalSumWaiter(@RequestParam Long waiterId,
                                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate day){
        return chequeService.getAmountForDay(waiterId,day);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/averageAmount")
    public AverageAmountRestaurantResponse getAverageAmount(@RequestParam Long restId,
                                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate day){
        return chequeService.getAverageAmount(restId,day);
    }
}
