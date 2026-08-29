package com.newadmission.DTO;

import java.util.Objects;

public record FeeFilterSummaryDTO(
        Double totalFees,
        Double paidFees,
        Double pendingFees
) {
    // Compact constructor to guarantee fields are never null
    public FeeFilterSummaryDTO {
        totalFees = Objects.requireNonNullElse(totalFees, 0.0);
        paidFees = Objects.requireNonNullElse(paidFees, 0.0);
        pendingFees = Objects.requireNonNullElse(pendingFees, 0.0);
    }

    // Merges data and returns a NEW immutable instance
    public FeeFilterSummaryDTO add(FeeFilterSummaryDTO other) {
        if (other == null) {
            return this;
        }
        return new FeeFilterSummaryDTO(
                this.totalFees() + other.totalFees(),
                this.paidFees() + other.paidFees(),
                this.pendingFees() + other.pendingFees()
        );
    }
}