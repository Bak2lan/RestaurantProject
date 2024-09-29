package java14.service;

import java14.dto.menuItemDTO.GlobalSearchResponse;
import java14.dto.menuItemDTO.MenuItemRequest;
import java14.dto.menuItemDTO.MenuItemResponse;
import java14.dto.messageDTO.SimpleResponse;

import java.util.List;

public interface MenuItemService {
    SimpleResponse saveToRestaurant(Long id, MenuItemRequest menuItemRequest);
    List<MenuItemResponse>getAllMenuItem();
    MenuItemResponse getById(Long id);
    SimpleResponse updateMenuItem(Long id,MenuItemRequest menuItemRequest);
    SimpleResponse deleteMenuItem(Long id);
    List<GlobalSearchResponse> globalSearch(String word);
    SimpleResponse assignMenuItemToSubCategory(Long menuItemId,Long subCategoryId);
    List<MenuItemResponse>sortByPrice(String ascOrDesc);
    List<MenuItemResponse>getAllById(List<Long>menuItems);
}
