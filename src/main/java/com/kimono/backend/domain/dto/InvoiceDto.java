package com.kimono.backend.domain.dto;

import com.kimono.backend.domain.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceDto {
    private Integer id;
    private Integer customerId;
    private Integer invoiceNumber;
    private String invoiceAddress;
    private String invoiceName;
    private List<Integer> productSnapshotIds;
    private PaymentMethod paymentMethod;
    private Integer totalAmountCents;
}
