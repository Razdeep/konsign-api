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
    String transport;
    String lrDate;

    List<LrPm> lrPmList;
    Float billAmount;

    public boolean anyFieldEmpty() {
        return supplierName == "" || buyerName == "" ||
                billNo == "" || billDate == "" || transport == "" ||
                lrDate == "" || billAmount == 0.f;
    }
}
