package com.razdeep.konsignapi.model;

import com.razdeep.konsignapi.entity.BillEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
public class Bill {
    String supplierName;
    String buyerName;
    String billNo;
    String billDate;
    String transportName;
    String lrDate;

    List<LrPm> lrPmList;
    Float billAmount;

    public Bill(BillEntity other) {
        this.supplierName = other.getSupplierEntity().getSupplierName();
        this.buyerName = other.getBuyerEntity().getBuyerName();
        this.billNo = other.getBillNo();
        this.billDate = other.getBillDate();
        this.transportName = other.getTransportEntity().getTransportName();
        this.lrDate = other.getLrDate();
        this.lrPmList = other.getLrPmEntityList().stream().map(lrPmEntity
                -> new LrPm(lrPmEntity.getLr(), lrPmEntity.getPm())).collect(Collectors.toList());
        this.billAmount = other.getBillAmount();
    }

    public boolean anyFieldEmpty() {
        return supplierName == "" || buyerName == "" ||
                billNo == "" || billDate == "" || transportName == "" ||
                lrDate == "" || billAmount == 0.f;
    }
}
