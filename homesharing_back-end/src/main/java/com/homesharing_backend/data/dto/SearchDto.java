package com.homesharing_backend.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
@NoArgsConstructor
public class SearchDto {

    private Long postID;
    private String title;
    private String address;
    private float price;
    private String imageUrl;
    private String nameVoucher;
    private Double avgStar;
    private int typeAccount;
    private String fullName;


    public SearchDto(Long postID, String title, String address,
                     float price, String imageUrl, String nameVoucher,
                     Double avgStar, int typeAccount) {
        this.postID = postID;
        this.title = title;
        this.address = address;
        this.price = price;
        this.imageUrl = imageUrl;
        this.nameVoucher = nameVoucher;
        this.avgStar = avgStar;
        this.typeAccount = typeAccount;
    }

    public SearchDto(Long postID, String title, String address,
                     float price, String imageUrl, String nameVoucher,
                     Double avgStar, int typeAccount, String fullName) {
        this.postID = postID;
        this.title = title;
        this.address = address;
        this.price = price;
        this.imageUrl = imageUrl;
        this.nameVoucher = nameVoucher;
        this.avgStar = avgStar;
        this.typeAccount = typeAccount;
        this.fullName = fullName;
    }
}
