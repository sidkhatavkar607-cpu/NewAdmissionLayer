package com.newadmission.Serviceimpl;

import com.newadmission.DTO.CollectionResponse;
import com.newadmission.Entity.AdmissionForm;
import com.newadmission.Entity.Installment;
import com.newadmission.Repository.AdmissionRepository;
import com.newadmission.Repository.InstallmentRepository;
import com.newadmission.Service.CollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CollectionServiceImpl implements CollectionService {

    private final AdmissionRepository admissionRepository;
    private final InstallmentRepository installmentRepository;
    private final StaffService staffService;

    @Override
    @Transactional(readOnly = true)
    public List<CollectionResponse> getCollections(
            LocalDate startDate,
            LocalDate endDate,
            String role,
            String email,
            String branchCode
    ) {

        if (!staffService.hasPermission(role, email, "GET")) {
            throw new RuntimeException("You dont have permission to access this resource");
        }

        List<CollectionResponse> collections =
                new ArrayList<>();

        /*
         * 1. ONE-TIME PAYMENTS
         */
        List<AdmissionForm> oneTimePayments =
                admissionRepository.findOneTimeCollections(
                        startDate,
                        endDate,
                        branchCode
                );

        for (AdmissionForm admission : oneTimePayments) {

            collections.add(
                    CollectionResponse.builder()
                            .id(admission.getId())
                            .studentName(admission.getName())
                            .registrationNo(
                                    admission.getRegistrationNo()
                            )
                            .courseName(
                                    admission.getCoursename()
                            )
                            .amount(
                                    admission.getPaidFees()
                            )
                            .paymentDate(
                                    admission.getPaymentDate()
                            )
                            .paymentMode(
                                    admission.getPaymentMode()
                            )
                            .transactionId(
                                    admission.getTransactionId()
                            )
                            .invoiceNo(null)
                            .paymentType("ONE_TIME")
                            .build()
            );
        }

        /*
         * 2. INSTALLMENT PAYMENTS
         */
        List<Installment> installments =
                installmentRepository.findPaidCollections(
                        startDate,
                        endDate,
                        branchCode
                );

        for (Installment installment : installments) {

            AdmissionForm admission =
                    installment.getAdmission();

            collections.add(
                    CollectionResponse.builder()
                            .id(installment.getId())
                            .studentName(
                                    admission != null
                                            ? admission.getName()
                                            : null
                            )
                            .registrationNo(
                                    admission != null
                                            ? admission.getRegistrationNo()
                                            : null
                            )
                            .courseName(
                                    admission != null
                                            ? admission.getCoursename()
                                            : null
                            )
                            .amount(
                                    installment.getAmount()
                            )
                            .paymentDate(
                                    installment.getInstallmentDate()
                            )
                            .paymentMode(
                                    installment.getPaidBy()
                            )
                            .transactionId(
                                    installment.getTransactionId()
                            )
                            .invoiceNo(
                                    installment.getInvoiceNo()
                            )
                            .paymentType("INSTALLMENT")
                            .build()
            );
        }

        /*
         * 3. COMBINE AND SORT
         *
         * Latest payment first.
         */
        collections.sort(
                Comparator.comparing(
                        CollectionResponse::getPaymentDate,
                        Comparator.nullsLast(
                                Comparator.reverseOrder()
                        )
                )
        );

        return collections;
    }
}