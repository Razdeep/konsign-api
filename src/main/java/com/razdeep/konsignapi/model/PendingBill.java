package com.razdeep.konsignapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PendingBill {
    String billNo;
    String supplierName;
    String buyerName;
    Double billAmount;
    Double pendingAmount;
}
