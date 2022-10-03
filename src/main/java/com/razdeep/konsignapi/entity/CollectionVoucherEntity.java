package com.razdeep.konsignapi.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "collection_vouchers")
@Data
public class CollectionVoucherEntity {

    @Id
    private String voucherNo;

    private String voucherDate;

    @OneToOne
    @JoinColumn(name = "buyer_buyer_id")
    private BuyerEntity buyer;

    @OneToMany(mappedBy = "collectionVoucher")
    private List<CollectionVoucherItemEntity> collectionVoucherItemEntityList;

}
