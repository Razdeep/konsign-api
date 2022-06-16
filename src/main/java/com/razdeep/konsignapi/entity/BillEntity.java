package com.razdeep.konsignapi.entity;

import com.razdeep.konsignapi.model.Bill;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bill")
@Data
public class BillEntity {
    @Id
    String billNo;
    String supplierName;
    String buyerName;
    String billDate;
    String transport;
    String lrDate;
    String billAmount;

    public BillEntity() {}

    public BillEntity(Bill bill) {
        this.billNo = bill.getBillNo();
        this.supplierName = bill.getSupplierName();
        this.buyerName = bill.getBuyerName();
        this.billDate = bill.getBillDate();
        this.transport = bill.getTransport();
        this.lrDate = bill.getLrDate();
        this.billAmount = getBillAmount();
    }
}
