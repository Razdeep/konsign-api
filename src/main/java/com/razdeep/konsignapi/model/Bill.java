package com.razdeep.konsignapi.model;

import com.razdeep.konsignapi.entity.BillEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
public class Bill {
    String supplierName;
    String buyerName;
    String billNo;
    LocalDate billDate;
    String transportName;
    LocalDate lrDate;

    List<LrPm> lrPmList;
    Double billAmount;

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
        return Objects.equals(supplierName, "") || Objects.equals(buyerName, "") ||
                Objects.equals(billNo, "") || Objects.equals(billDate, "") ||
                Objects.equals(transportName, "") || Objects.equals(lrDate, "") ||
                billAmount == 0.f;
    }
}
