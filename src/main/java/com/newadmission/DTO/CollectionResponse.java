package com.newadmission.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollectionResponse {

    private Long id;

    private String studentName;

    private String registrationNo;

    private String courseName;

    private Double amount;

    private LocalDate paymentDate;

    private String paymentMode;

    private String transactionId;

    private String invoiceNo;

    private String paymentType;
}