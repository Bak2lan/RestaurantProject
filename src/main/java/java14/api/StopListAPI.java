package java14.api;

import java14.dto.messageDTO.SimpleResponse;
import java14.dto.stopListDTO.StopListRequest;
import java14.dto.stopListDTO.StopListResponse;
import java14.service.StopListService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stopLists")
@RequiredArgsConstructor
public class StopListAPI {
    private final StopListService stopListService;

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PostMapping ("/food/{menuId}")
    public SimpleResponse saveStopList(@PathVariable Long menuId,
                                       @RequestBody StopListRequest stopListRequest){
        return stopListService.saveStopList(menuId,stopListRequest);

    }

    @PermitAll
    @GetMapping("/{id}")
    public StopListResponse getStopList(@PathVariable Long id){
        return stopListService.getByIdStopList(id);
    }

    @PermitAll
    @GetMapping
    public List<StopListResponse>getAll(){
        return stopListService.getAllStopLists();
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PutMapping("/{id}")
    public SimpleResponse updateStopList(@PathVariable Long id,
                                         @RequestBody StopListRequest stopListRequest){
        return stopListService.updateStopList(id,stopListRequest);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @DeleteMapping("/{id}")
    public SimpleResponse deleteStopList(@PathVariable Long id){
        return stopListService.delete(id);
    }
}
