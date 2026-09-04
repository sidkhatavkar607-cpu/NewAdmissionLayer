package com.newadmission.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientBranchDTO {
    private Long bid;
    private String branchName;
    private String branchEmail;
    private String branchCode;
    private String contact;
    private String branchHeadName;
    private String address;
    private String city;
    private String district;
    private String state;
    private String country;
    private Integer pincode;
    private String status;
    private String branchImage;
    private String instituteEmail;
    private String gmapLink;
    private String websiteLink;
}