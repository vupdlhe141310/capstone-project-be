package com.homesharing_backend.presentation.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {

    private Date startDate;
    private Date endDate;
    private String note;
    private float totalMoney;
    private int totalPerson;
    private List<Long> postServices;
    private Long postVoucherID;
    private float totalPriceRoom;
    private float totalPriceService;
    private float discount;
    private String fullName;
    private String mobile;
    private String email;
}
