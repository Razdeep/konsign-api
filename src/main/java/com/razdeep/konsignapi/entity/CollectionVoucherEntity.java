package com.razdeep.konsignapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "collection_vouchers")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionVoucherEntity {

    @Id
    private String voucherNo;

    private String voucherDate;

    @OneToOne
    @JoinColumn(name = "buyer_buyer_id")
    private BuyerEntity buyer;

    @OneToMany(mappedBy = "collectionVoucher", cascade = CascadeType.ALL)
    private List<CollectionVoucherItemEntity> collectionVoucherItemEntityList;

}
