package com.razdeep.konsignapi.entity;

import com.razdeep.konsignapi.model.Bill;
import lombok.Data;
import lombok.val;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    Float billAmount;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "billEntry")
    List<LrPmEntity> lrPmEntityList;

    public BillEntity() {}

    public BillEntity(Bill bill) {
        this.billNo = bill.getBillNo();
        this.supplierName = bill.getSupplierName();
        this.buyerName = bill.getBuyerName();
        this.billDate = bill.getBillDate();
        this.transport = bill.getTransport();
        this.lrDate = bill.getLrDate();
        this.billAmount = bill.getBillAmount();
        this.lrPmEntityList = new ArrayList<>();
        if (bill.getLrPmList() != null) {
            val lrPmList = bill.getLrPmList();
            for (int i = 0; i < lrPmList.size(); ++i) {
                val lrPmEntity = new LrPmEntity(lrPmList.get(i));
                lrPmEntity.setBillEntry(this);
                lrPmEntityList.add(lrPmEntity);
            }
        }
    }
}
