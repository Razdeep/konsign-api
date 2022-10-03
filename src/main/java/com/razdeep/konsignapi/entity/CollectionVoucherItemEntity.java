package com.razdeep.konsignapi.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "collection_voucher_item")
@Data
public class CollectionVoucherItemEntity {

    @Id
    @GeneratedValue
    @Column(name = "collection_voucher_item_id")
    private String collectionVoucherItemId;

    @ManyToOne
    @JoinColumn(name = "fk_collection_voucher_id")
    private CollectionVoucherEntity collectionVoucher;

    @OneToOne
    @JoinColumn(name = "bill_bill_no")
    BillEntity bill;

    Float amountCollected;

    String bank;

    String ddNo;

    String ddDate;
}
