package java14.api;
import java14.dto.menuItemDTO.GlobalSearchResponse;
import java14.dto.menuItemDTO.MenuItemRequest;
import java14.dto.menuItemDTO.MenuItemResponse;
import java14.dto.messageDTO.SimpleResponse;
import java14.service.MenuItemService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menuItems")
@RequiredArgsConstructor
public class MenuItemAPI {
    private final MenuItemService menuItemService;

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PostMapping("/{restaurantId}")
    public SimpleResponse saveToRestaurant(@PathVariable Long restaurantId,
                                           @RequestBody MenuItemRequest menuItemRequest){
        return menuItemService.saveToRestaurant(restaurantId,menuItemRequest);
    }


    @PermitAll
    @GetMapping("/{id}")
    public MenuItemResponse getById(@PathVariable Long id){
        return menuItemService.getById(id);
    }

    @PermitAll
    @GetMapping
    public List<MenuItemResponse> getAllMenu(){
        return menuItemService.getAllMenuItem();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PutMapping("/{id}")
    public SimpleResponse update(@PathVariable Long id,
                                 @RequestBody MenuItemRequest menuItemRequest){
        return menuItemService.updateMenuItem(id,menuItemRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @DeleteMapping("/{id}")
    public SimpleResponse deleteMenuItem(@PathVariable Long id){
        return menuItemService.deleteMenuItem(id);
    }

    @PermitAll
    @GetMapping("/globalSearch")
    public List<GlobalSearchResponse> globalSearchResponse(@RequestParam String word){
        return menuItemService.globalSearch(word);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PostMapping("/{menuItemId}/{subCategoryId}")
    public SimpleResponse assignSubCategoryToMenuItem(@PathVariable Long menuItemId,
                                                      @PathVariable Long subCategoryId){
        return menuItemService.assignMenuItemToSubCategory(menuItemId,subCategoryId);
    }
    @PermitAll
    @GetMapping("/sortPrice")
    public List<MenuItemResponse>sortByPrice(@RequestParam String ascOrDesc){
        return menuItemService.sortByPrice(ascOrDesc);
    }
}
