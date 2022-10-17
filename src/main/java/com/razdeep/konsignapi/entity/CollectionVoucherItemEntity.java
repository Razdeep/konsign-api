package com.razdeep.konsignapi.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "collection_voucher_item")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollectionVoucherItemEntity {

    @Id
    @GeneratedValue
    @Column(name = "collection_voucher_item_id")
    private Integer collectionVoucherItemId;

    @ManyToOne
    @JoinColumn(name = "fk_collection_voucher_id")
    private CollectionVoucherEntity collectionVoucher;

    @OneToOne
    @JoinColumn(name = "bill_bill_no")
    BillEntity bill;

    Double amountCollected;

    String bank;

    String ddNo;

    String ddDate;
}
