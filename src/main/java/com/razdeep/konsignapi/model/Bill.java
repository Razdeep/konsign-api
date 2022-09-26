package com.razdeep.konsignapi.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Bill {
    String supplierName;
    String buyerName;
    String billNo;
    String billDate;
    String transportName;
    String lrDate;

    List<LrPm> lrPmList;
    Float billAmount;

    public boolean anyFieldEmpty() {
        return supplierName == "" || buyerName == "" ||
                billNo == "" || billDate == "" || transportName == "" ||
                lrDate == "" || billAmount == 0.f;
    }
}
