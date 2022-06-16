package com.razdeep.konsignapi.model;

import lombok.Data;

@Data
public class Bill {
    String supplierName;
    String buyerName;
    String billNo;
    String billDate;
    String transport;
    String lrDate;
//    String lrPm = "";
    String billAmount;

    public boolean anyFieldEmpty() {
        return supplierName == "" || buyerName == "" ||
                billNo == "" || billDate == "" || transport == "" ||
                lrDate == "" || billAmount == "";
    }
}
