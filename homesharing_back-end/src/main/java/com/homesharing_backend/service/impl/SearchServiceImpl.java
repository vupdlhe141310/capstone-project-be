package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.dto.SearchDto;
import com.homesharing_backend.data.repository.DistrictRepository;
import com.homesharing_backend.data.repository.PostRepository;
import com.homesharing_backend.exception.NotFoundException;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.presentation.payload.request.SearchFilterRequest;
import com.homesharing_backend.presentation.payload.request.SearchRequest;
import com.homesharing_backend.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Override
    public ResponseEntity<ResponseObject> searchByTitlePostOrLocation(SearchRequest searchRequest, int indexPage) {

        int size = 10;
        int page = indexPage - 1;

        if (Objects.isNull(searchRequest)) {
            throw new NotFoundException("Search null");
        } else {
            Page<SearchDto> searchDtoListByTitle = postRepository.listSearchPostByTitle(searchRequest.getSearchText(), PageRequest.of(page, size));

            List<SearchDto> list = new ArrayList<>();

            if (Objects.isNull(searchDtoListByTitle)) {
                throw new NotFoundException("search khong co data");
            } else {
                searchDtoListByTitle.forEach(s -> {
                    SearchDto dto = SearchDto.builder()
                            .postID(s.getPostID())
                            .title(s.getTitle())
                            .address(s.getAddress())
                            .imageUrl(s.getImageUrl())
                            .price(s.getPrice())
                            .fullName(s.getFullName())
                            .nameVoucher(s.getNameVoucher())
                            .typeAccount(s.getTypeAccount())
                            .avgStar(s.getAvgStar())
                            .build();
                    list.add(dto);
                });
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("search success", new HashMap<>() {
                {
                    put("searchList", list);
                    put("sizePage", searchDtoListByTitle.getTotalPages());
                }
            }));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> searchFilter(SearchFilterRequest searchFilterRequest, int indexPage) {

        int size = 10;
        int page = indexPage - 1;

        Page<SearchDto> searchDtos = postRepository.getSearchFilter(searchFilterRequest.getVoucherPercent(),
                searchFilterRequest.getMinPrice(), searchFilterRequest.getMaxPrice(), searchFilterRequest.getRoomTypeID(),
                searchFilterRequest.getNumberOfGuest(), searchFilterRequest.getRoomTypeID(), searchFilterRequest.getMinStar(),
                searchFilterRequest.getMaxStar(), searchFilterRequest.getStartDate(), PageRequest.of(page, size));

        List<SearchDto> list = new ArrayList<>();

        if (Objects.isNull(searchDtos)) {
            throw new NotFoundException("search khong co data");
        } else {
            searchDtos.forEach(s -> {
                SearchDto dto = SearchDto.builder()
                        .postID(s.getPostID())
                        .title(s.getTitle())
                        .address(s.getAddress())
                        .imageUrl(s.getImageUrl())
                        .price(s.getPrice())
                        .fullName(s.getFullName())
                        .nameVoucher(s.getNameVoucher())
                        .typeAccount(s.getTypeAccount())
                        .avgStar(s.getAvgStar())
                        .build();
                list.add(dto);
            });
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("search success", new HashMap<>() {
            {
                put("searchList", searchDtos);
                put("sizePage", searchDtos.getTotalPages());
            }
        }));
    }
}
