package java14.service;

import java14.dto.messageDTO.SimpleResponse;
import java14.dto.stopListDTO.StopListRequest;
import java14.dto.stopListDTO.StopListResponse;

import java.util.List;

public interface StopListService {
    SimpleResponse saveStopList(Long menuId,StopListRequest stopListRequest);
    StopListResponse getByIdStopList(Long id);
    List<StopListResponse>getAllStopLists();
    SimpleResponse updateStopList(Long id,StopListRequest stopListRequest);
    SimpleResponse delete(Long id);

}
