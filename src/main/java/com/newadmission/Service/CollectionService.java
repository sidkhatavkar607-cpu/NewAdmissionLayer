package com.newadmission.Service;

import com.newadmission.DTO.CollectionResponse;

import java.time.LocalDate;
import java.util.List;

public interface CollectionService {

    List<CollectionResponse> getCollections(
            LocalDate startDate,
            LocalDate endDate,
            String role,
            String email,
            String branchCode
    );
}