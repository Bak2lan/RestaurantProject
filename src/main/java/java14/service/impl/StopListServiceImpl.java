package java14.service.impl;

import java14.dto.menuItemDTO.MenuItemStopListResponse;
import java14.dto.messageDTO.SimpleResponse;
import java14.dto.stopListDTO.StopListRequest;
import java14.dto.stopListDTO.StopListResponse;
import java14.entity.MenuItem;
import java14.entity.StopList;
import java14.repository.MenuItemRepository;
import java14.repository.StopListRepository;
import java14.service.StopListService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class StopListServiceImpl implements StopListService {
    private final StopListRepository stopListRepository;
    private final MenuItemRepository menuItemRepository;

    @Override
    public SimpleResponse saveStopList(Long menuId, StopListRequest stopListRequest) {
        MenuItem menuItem = menuItemRepository.findById(menuId).orElseThrow(() -> new NoSuchElementException(String.format("Not found MenuItem with id %s ", menuId)));
        StopList stopList= new StopList();
        stopList.setDate(stopListRequest.getDate());
        stopList.setReason(stopListRequest.getReason());
        stopList.setMenuItem(menuItem);
        stopListRepository.save(stopList);

        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("Stop list saved")
                .build();
    }

    @Override
    public StopListResponse getByIdStopList(Long id) {
        StopList stopList = stopListRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("Not found stop list with id %s ", id)));
        MenuItem menuItem = stopList.getMenuItem();
        MenuItemStopListResponse menuItemResponse= new MenuItemStopListResponse(
                menuItem.getName(),
                menuItem.getImage(),
                menuItem.getPrice()
        );

        StopListResponse stopListResponse= new StopListResponse();
        stopListResponse.setReason(stopList.getReason());
        stopListResponse.setDate(stopList.getDate());
        stopListResponse.setFood(menuItemResponse);
        return stopListResponse;
    }

    @Override
    public List<StopListResponse> getAllStopLists() {
        List<StopList> all = stopListRepository.findAll();
        List<StopListResponse>stopListResponses= new ArrayList<>();
        for (StopList stopList:all){
            MenuItemStopListResponse menuItemStopListResponse= new MenuItemStopListResponse(
                    stopList.getMenuItem().getName(),
                    stopList.getMenuItem().getImage(),
                    stopList.getMenuItem().getPrice()
            );

            StopListResponse stopListResponse= new StopListResponse(
                    menuItemStopListResponse,
                    stopList.getReason(),
                    stopList.getDate()
            );
            stopListResponses.add(stopListResponse);
        }
        return stopListResponses;
    }

    @Override
    public SimpleResponse updateStopList(Long id, StopListRequest stopListRequest) {
        StopList stopList = stopListRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("Not found stop list with id %s", id)));
        stopList.setDate(stopListRequest.getDate());
        stopList.setReason(stopListRequest.getReason());
        stopListRepository.save(stopList);
        return new SimpleResponse(
                HttpStatus.OK,
                "Stop List updated"
        );
    }

    @Override
    public SimpleResponse delete(Long id) {
        StopList stopList = stopListRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("Not found stop list with id %s", id)));
        stopList.setMenuItem(null);
        stopListRepository.deleteById(stopList.getId());
        return new SimpleResponse(
                HttpStatus.OK,
                "Stop list deleted"
        );
    }
}
