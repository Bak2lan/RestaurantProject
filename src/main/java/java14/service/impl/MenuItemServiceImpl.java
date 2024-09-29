package java14.service.impl;

import java14.dto.menuItemDTO.GlobalSearchResponse;
import java14.dto.menuItemDTO.MenuItemRequest;
import java14.dto.menuItemDTO.MenuItemResponse;
import java14.dto.messageDTO.SimpleResponse;
import java14.entity.MenuItem;
import java14.entity.Restaurant;
import java14.entity.SubCategory;
import java14.exception.MyException;
import java14.repository.MenuItemRepository;
import java14.repository.RestaurantRepository;
import java14.repository.SubCategoryRepository;
import java14.service.MenuItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final SubCategoryRepository subCategoryRepository;

    @Override
    public SimpleResponse saveToRestaurant(Long id, MenuItemRequest menuItemRequest) {
        try {
            Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("Not  found Restaurant with id %s", id)));
            MenuItem menuItem = new MenuItem();
            menuItem.setName(menuItemRequest.getName());
            menuItem.setDescription(menuItemRequest.getDescription());
            if (menuItemRequest.getPrice()<=0){
                throw new MyException("Price of foods can not be negative");
            }else {
                menuItem.setPrice(menuItemRequest.getPrice());
                menuItem.setImage(menuItemRequest.getImage());
                menuItem.setVegetarian(menuItemRequest.isVegetarian());
                menuItem.setRestaurant(restaurant);
                menuItemRepository.save(menuItem);
            }
            return SimpleResponse
                    .builder()
                    .httpStatus(HttpStatus.OK)
                    .message("MenuItem successfully saved to Restaurant with id " + id)
                    .build();
        }catch (MyException e){
            System.out.println(e.getMessage());
        }
        return null;

    }

    @Override
    public List<MenuItemResponse> getAllMenuItem() {
        return menuItemRepository.getAllMenuItem();
    }

    @Override
    public MenuItemResponse getById(Long id) {
        return menuItemRepository.getByIdMenu(id);
    }

    @Override
    public SimpleResponse updateMenuItem(Long id, MenuItemRequest menuItemRequest) {
        MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("Not found MenuItem with id %s", id)));
        menuItem.setName(menuItemRequest.getName());
        menuItem.setDescription(menuItemRequest.getDescription());
        menuItem.setPrice(menuItemRequest.getPrice());
        menuItem.setImage(menuItemRequest.getImage());
        menuItem.setVegetarian(menuItemRequest.isVegetarian());
        menuItemRepository.save(menuItem);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("MenuItem with id " +id+" is updated")
                .build();
    }

    @Override
    public SimpleResponse deleteMenuItem(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("Not found MenuItem with id %s", id)));
        menuItemRepository.deleteById(menuItem.getId());
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("MenuItem with id " +id+" is deleted")
                .build();
    }

    @Override
    public List<GlobalSearchResponse> globalSearch(String word) {
        return menuItemRepository.globalSearch(word);
    }

    @Override
    public SimpleResponse assignMenuItemToSubCategory(Long menuItemId, Long subCategoryId) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(() -> new NoSuchElementException(String.format("Not found MenuItem with id %s", menuItemId)));
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId).orElseThrow(() -> new NoSuchElementException(String.format("Not found subCategory with id %s", subCategoryId)));
        menuItem.setSubCategory(subCategory);
        return SimpleResponse
                .builder().httpStatus(HttpStatus.OK)
                .message("SubCategory successfully assigned to MenuItem")
                .build();
    }

    @Override
    public List<MenuItemResponse> sortByPrice(String ascOrDesc) {
        if (ascOrDesc.equalsIgnoreCase("asc")){
            return menuItemRepository.sortByPrice();
        } else if (ascOrDesc.equalsIgnoreCase("desc")) {
            return menuItemRepository.sortByPriceDesc();
        }
        return null;
    }

    @Override
    public List<MenuItemResponse> getAllById(List<Long> menuItems) {
        return menuItemRepository.getAllMenuById(menuItems);
    }
}
