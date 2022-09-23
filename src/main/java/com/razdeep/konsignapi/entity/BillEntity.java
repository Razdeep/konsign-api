package com.razdeep.konsignapi.entity;

import com.razdeep.konsignapi.model.Bill;
import com.razdeep.konsignapi.model.Supplier;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bill")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillEntity {
    @Id
    private String billNo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_supplier_id", nullable = false)
    private SupplierEntity supplierEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_buyer_id", nullable = false)
    private BuyerEntity buyerEntity;

    private String billDate;
    private String transport;
    private String lrDate;
    private Float billAmount;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "billEntry")
    private List<LrPmEntity> lrPmEntityList;

//    public BillEntity() {}

//    public BillEntity(Bill bill) {
//        this.billNo = bill.getBillNo();
//        this.supplierName = bill.getSupplierName();
//        this.buyerName = bill.getBuyerName();
//        this.billDate = bill.getBillDate();
//        this.transport = bill.getTransport();
//        this.lrDate = bill.getLrDate();
//        this.billAmount = bill.getBillAmount();
//        this.lrPmEntityList = new ArrayList<>();
//        if (bill.getLrPmList() != null) {
//            val lrPmList = bill.getLrPmList();
//            for (int i = 0; i < lrPmList.size(); ++i) {
//                val lrPmEntity = new LrPmEntity(lrPmList.get(i));
//                lrPmEntity.setBillEntry(this);
//                lrPmEntityList.add(lrPmEntity);
//            }
//        }
//    }
}
